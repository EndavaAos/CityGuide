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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cityguide.R
import com.example.cityguide.data.models.LocationPOIScreen
import com.example.cityguide.data.repository.LocationRepositoryImpl
import com.example.cityguide.data.responses.Resource
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_location_search.view.*
import kotlinx.android.synthetic.main.fragment_poi_screen.*
import kotlinx.android.synthetic.main.fragment_poi_screen.view.*
import kotlinx.android.synthetic.main.item_poi.view.*
import javax.inject.Inject

class POIScreenFragment : Fragment(R.layout.fragment_poi_screen) {

    @Inject
    lateinit var vm: LocationSearchVM

    @Inject
    lateinit var locationRepositoryImpl: LocationRepositoryImpl

    lateinit var recyclerViewAdapter: RecyclerViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)?.visibility = View.INVISIBLE

        val intent: Intent? = activity?.intent

        val placeToSearch = intent?.getStringExtra("place")

        view?.rootView?.locationNameText?.text = placeToSearch + " trip"

        val recycleView : RecyclerView? = view?.rootView?.rv
        view?.rootView?.backArrowButton?.setOnClickListener {
            activity?.finish()
        }

        view?.rootView?.scheduleTripButton?.setOnClickListener {
            var totalPoints: Int = 0
            var allUnChecked: Boolean = false

            for(item in recyclerViewAdapter.locations){
                allUnChecked = item.isChecked.or(allUnChecked)
                if(item.isChecked) {
                    totalPoints += 1
                }
            }
            if(allUnChecked == false){
                val confirmationDialog: ConfirmationDialogFragment = ConfirmationDialogFragment()
                confirmationDialog.show(childFragmentManager,"Confirmation Dialog")
            } else {

            }
        }

        if (placeToSearch != null) {
            vm.getLocation(placeToSearch, "5ae2e3f221c38a28845f05b6dd571f66600ae5630f709863edc61b5d")
        }

        vm.locationLivedata.observe(viewLifecycleOwner,{
            when (it) {
                is Resource.Success -> {
                    //Toast.makeText(context, it.data.toString(), Toast.LENGTH_LONG).show()
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
                    val locations = mutableListOf<LocationPOIScreen>()
                    it.data?.features?.forEach { it2 -> locations.add(LocationPOIScreen(it2.properties.name, it2.properties.kinds, false, it2.properties.xid)) }
                    recyclerViewAdapter = RecyclerViewAdapter(locations, requireContext())
                    recycleView?.adapter = recyclerViewAdapter
                    recycleView?.layoutManager = LinearLayoutManager(context)
                    if(locations.size == 0){
                        errorDisplay()
                    }
                    else
                    {
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)

    }
}