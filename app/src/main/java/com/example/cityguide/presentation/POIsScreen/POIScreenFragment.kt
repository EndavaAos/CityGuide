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
import com.example.cityguide.data.models.LocationPOIScreenCheck
import com.example.cityguide.data.models.ListOfPOI
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

        view?.rootView?.locationNameText?.text = placeToSearch + " trip"

        val recycleView : RecyclerView? = view?.rootView?.rv


        view?.rootView?.backArrowButton?.setOnClickListener {
            activity?.finish()
        }

        view?.rootView?.scheduleTripButton?.setOnClickListener {
            var allUnChecked: Boolean = false
            val listOfTrips: ListOfPOI = ListOfPOI(mutableListOf(),null,null)

           recyclerViewAdapter.locations.forEachIndexed { index, locationPOIScreen ->

                if(recyclerViewAdapter.locations[index].isChecked && recyclerViewAdapter.locations[index].name == data.features[index].properties.name){

                    listOfTrips.listOfPoints.add(data.features[index])
                }
            }
            for(item in recyclerViewAdapter.locations){
                allUnChecked = item.isChecked.or(allUnChecked)
            }
            if(allUnChecked == false){
                val trips = Trips(0, name, country, arrayListOf(), null, null)
                val confirmationDialog: ConfirmationDialogFragment = ConfirmationDialogFragment(trips)
                confirmationDialog.show(childFragmentManager,"Confirmation Dialog")
            }
            else{
                var listOfPOI: MutableList<String> = mutableListOf()
                listOfTrips.listOfPoints.forEach { poi_item ->
                    listOfPOI.add(poi_item.properties.xid)
                }
                vm.getPoiDetailsForList(listOfPOI, "5ae2e3f221c38a28845f05b6dd571f66600ae5630f709863edc61b5d")
                vm.poiDetalisLiveDataList.observe(viewLifecycleOwner,{
                    when (it) {
                        is Resource.Success -> {
                            //Toast.makeText(context, it.data.toString(), Toast.LENGTH_LONG).show()
                            it.data?.let { it1 -> val poi_List: List<Trips.Trip> = it.data.listOfTrip
                                setDateForTrip(poi_List, name, country) }
                        }
                        is Resource.Error -> {
                            Toast.makeText(context, "error: ${it.message}", Toast.LENGTH_LONG).show()
                        }
                        is Resource.Loading -> {
                        }
                    }
                })
                //Toast.makeText(context, listOfPOI.toString(), Toast.LENGTH_LONG).show()
                //Toast.makeText(context, trips.toString(), Toast.LENGTH_LONG).show()
                //vm.getPoiDetailsForList(listOfIds, "5ae2e3f221c38a28845f05b6dd571f66600ae5630f709863edc61b5d")
                /*val action = POIScreenFragmentDirections.navigateFromPOIScreenToMakeTripFragment(listOfTrips.listOfPoints.size, placeToSearch!!)
                findNavController().navigate(action)*/
            }
        }

        if (placeToSearch != null) {
            vm.getLocation(placeToSearch, "5ae2e3f221c38a28845f05b6dd571f66600ae5630f709863edc61b5d")
        }

        vm.locationLivedata.observe(viewLifecycleOwner,{
            when (it) {
                is Resource.Success -> {
                    //Toast.makeText(context, it.data.toString(), Toast.LENGTH_LONG).show()
                    country = it.data?.country.toString()
                    name = it.data?.name.toString()
                    //Toast.makeText(context, trips.toString(), Toast.LENGTH_LONG).show()
                }
                is Resource.Error -> {
                    errorDisplay()
                }
                is Resource.Loading -> {
                }
            }
        })

        vm.suggestionLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    val locations = mutableListOf<LocationPOIScreenCheck>()
                    it.data?.features?.forEach { it2 -> locations.add(LocationPOIScreenCheck(it2.properties.name, it2.properties.kinds, false, it2.properties.xid)) }
                    recyclerViewAdapter = RecyclerViewAdapter(locations, requireContext())
                    recycleView?.adapter = recyclerViewAdapter
                    recycleView?.layoutManager = LinearLayoutManager(context)
                    if(locations.size == 0){
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

    fun errorDisplay(){
        view?.poiText?.visibility = View.INVISIBLE
        view?.descriptionText?.visibility = View.INVISIBLE
        view?.scheduleTripButton?.visibility = View.INVISIBLE
        view?.backArrowButton?.visibility = View.VISIBLE
        view?.tripNotFoundCard?.visibility = View.VISIBLE
    }

    fun okayDisplay(){
        view?.poiText?.visibility = View.VISIBLE
        view?.descriptionText?.visibility = View.VISIBLE
        view?.scheduleTripButton?.visibility = View.VISIBLE
        view?.backArrowButton?.visibility = View.INVISIBLE
        view?.tripNotFoundCard?.visibility = View.INVISIBLE
    }

    fun setDateForTrip(listPoi: List<Trips.Trip>, name: String, country: String){
        val trips = Trips(0, name, country, listPoi, null, null)
        val action = POIScreenFragmentDirections.navigateFromPOIScreenToMakeTripFragment(trips)
        view?.let { Navigation.findNavController(it).navigate(action) }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)

    }
}