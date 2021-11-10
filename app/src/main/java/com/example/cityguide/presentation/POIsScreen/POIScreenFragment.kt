package com.example.cityguide.presentation.POIsScreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cityguide.R
import com.example.cityguide.data.db.entity.Trips
import com.example.cityguide.data.models.ListOfPOI
import com.example.cityguide.data.models.LocationPOIScreenCheck
import com.example.cityguide.data.repository.LocationRepositoryImpl
import com.example.cityguide.data.responses.Resource
import com.example.cityguide.data.responses.SuggestionResponse
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_poi_screen.view.*
import javax.inject.Inject

class POIScreenFragment : Fragment(R.layout.fragment_poi_screen) {

    @Inject
    lateinit var vm: LocationSearchVM

    @Inject
    lateinit var locationRepositoryImpl: LocationRepositoryImpl

    lateinit var recyclerViewAdapter: RecyclerViewAdapter

    lateinit var data: SuggestionResponse

    lateinit var trips: Trips


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        //Trips Atributes
        var name: String = ""
        var country: String = ""


        val intent: Intent? = activity?.intent
        val placeToSearch = intent?.getStringExtra("place")
        val databaseAction = intent?.getStringExtra("database")
        var idDatabase = 0
        if(databaseAction == "Update") {
            trips = intent?.getSerializableExtra("trip") as Trips
            idDatabase = trips.id
        }

        view?.rootView?.locationNameText?.text = placeToSearch + " trip"


        val recycleView: RecyclerView? = view?.rootView?.rv
      
        view?.rootView?.backArrowButton?.setOnClickListener {
            activity?.finish()
        }

        view?.rootView?.scheduleTripButton?.setOnClickListener {
            var allUnChecked = false
            val listOfTrips = ListOfPOI(mutableListOf(),null,null)

           recyclerViewAdapter.locations.forEachIndexed { index, locationPOIScreen ->

                if(recyclerViewAdapter.locations[index].isChecked && findIfSameName(recyclerViewAdapter.locations[index].name, data)){
                    listOfTrips.listOfPoints.add(data.features[index])
                }
            }
            for(item in recyclerViewAdapter.locations){
                allUnChecked = item.isChecked.or(allUnChecked)
            }
            if(allUnChecked == false){
                var id = 0
                if(databaseAction == "Update"){
                    id = idDatabase
                }
                val trips = Trips(id, name, country, arrayListOf(), null, null)
                val confirmationDialog = ConfirmationDialogFragment(trips, databaseAction!!)
                confirmationDialog.show(childFragmentManager,"Confirmation Dialog")
            }
            else{
                println("Size + " + listOfTrips.listOfPoints.size)
                var listOfPOI: MutableList<String> = mutableListOf()
                listOfTrips.listOfPoints.forEach { poi_item ->
                    listOfPOI.add(poi_item.properties.xid)
                }
                vm.getPoiDetailsForList(listOfPOI, "5ae2e3f221c38a28845f05b6dd571f66600ae5630f709863edc61b5d")
                vm.poiDetalisLiveDataList.observe(viewLifecycleOwner,{
                    when (it) {
                        is Resource.Success -> {
                            it.data?.let { it1 -> val poi_List: List<Trips.Trip> = it.data.listOfTrip
                                setDateForTrip(poi_List, name, country, databaseAction!!, idDatabase) }
                        }
                        is Resource.Error -> {
                            Toast.makeText(context, "error: ${it.message}", Toast.LENGTH_LONG).show()
                        }
                        is Resource.Loading -> {
                        }
                    }
                })
            }
        }

        if (placeToSearch != null) {
            vm.getLocation(
                placeToSearch,
                "5ae2e3f221c38a28845f05b6dd571f66600ae5630f709863edc61b5d"
            )
        }

        vm.locationLivedata.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    country = it.data?.country.toString()
                    name = it.data?.name.toString()
                }
                is Resource.Error -> {
                    errorDisplay()
                }
                is Resource.Loading -> {
                }
            }
        })

        fun String.capitalizeWords(): String = split(" ").joinToString(" ") { it.capitalize() }

        vm.suggestionLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {

                    val locations = mutableListOf<LocationPOIScreenCheck>()
                    var index = 0
                    it.data?.features?.forEach { it2 ->
                        if(databaseAction == "Insert") {
                            locations.add(
                                LocationPOIScreenCheck(
                                    it2.properties.name,
                                    it2.properties.kinds.replace("_", " ").capitalizeWords(),
                                    false,
                                    it2.properties.xid
                                )
                            )
                        }
                        if(databaseAction == "Update") {
                            if(findIfChecked(trips, it2.properties.name)) {
                                index += 1
                                locations.add(0,
                                    LocationPOIScreenCheck(
                                        it2.properties.name,
                                        it2.properties.kinds.replace("_", " ").capitalizeWords(),
                                        true,
                                        it2.properties.xid
                                    )
                                )
                            }else{
                                locations.add(locations.size,
                                    LocationPOIScreenCheck(
                                        it2.properties.name,
                                        it2.properties.kinds.replace("_", " ").capitalizeWords(),
                                        false,
                                        it2.properties.xid
                                    )
                                )
                            }
                        }
                    }
                    recyclerViewAdapter = RecyclerViewAdapter(locations, requireContext(), index)
                    recycleView?.adapter = recyclerViewAdapter
                    recycleView?.layoutManager = LinearLayoutManager(context)
                    if (locations.size == 0) {
                        errorDisplay()
                    }
                    else
                    {
                        data = it.data!!
                        okayDisplay()
                    }
                }
                is Resource.Error -> {
                    errorDisplay()
                }
                is Resource.Loading -> {

                }
            }
        })

        return view
    }

    fun errorDisplay() {
        view?.poiText?.visibility = View.INVISIBLE
        view?.descriptionText?.visibility = View.INVISIBLE
        view?.scheduleTripButton?.visibility = View.INVISIBLE
        view?.backArrowButton?.visibility = View.VISIBLE
        view?.tripNotFoundCard?.visibility = View.VISIBLE
    }

    fun okayDisplay() {
        view?.poiText?.visibility = View.VISIBLE
        view?.descriptionText?.visibility = View.VISIBLE
        view?.scheduleTripButton?.visibility = View.VISIBLE
        view?.backArrowButton?.visibility = View.INVISIBLE
        view?.tripNotFoundCard?.visibility = View.INVISIBLE
    }

    fun findIfChecked(trips: Trips, name: String): Boolean{
        trips.listOfPOI.forEach {
            if(it.name == name){
                return true
            }
        }
        return false
    }

    fun findIfSameName(name: String, trips: SuggestionResponse): Boolean{
        trips.features.forEach {
            if(it.properties.name == name){
                return true
            }
        }
        return false
    }

    fun setDateForTrip(listPoi: List<Trips.Trip>, name: String, country: String, databaseAction: String, idDatabase: Int){
        var id = 0
        if(databaseAction == "Update"){
            id = idDatabase
        }
        val trips = Trips(id, name, country, listPoi, null, null)
        val action = POIScreenFragmentDirections.navigateFromPOIScreenToMakeTripFragment(trips, databaseAction)
        view?.let { Navigation.findNavController(it).navigate(action) }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)

    }
}