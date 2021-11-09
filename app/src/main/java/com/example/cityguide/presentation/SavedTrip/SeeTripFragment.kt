package com.example.cityguide.presentation.SavedTrip

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cityguide.R
import com.example.cityguide.StartScreenActivity
import com.example.cityguide.data.db.entity.Trips
import com.example.cityguide.data.models.LocationPOIScreen
import com.example.cityguide.data.responses.Resource
import com.example.cityguide.presentation.POIsScreen.LocationSearchVM
import com.example.cityguide.presentation.POIsScreen.POIScreenActivity
import com.example.cityguide.presentation.POIsScreen.RecyclerViewAdapter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_poi_screen.view.*
import kotlinx.android.synthetic.main.fragment_see_trip.*
import kotlinx.android.synthetic.main.fragment_see_trip.view.*
import kotlinx.android.synthetic.main.fragment_see_trip.view.locationNameText
import kotlinx.android.synthetic.main.trips_fragment_general_trips_list_item.*
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class SeeTripFragment : Fragment() {

    lateinit var recyclerViewAdapter: POIAdapter

    private val expandableButtonAnimationDuration = 500L
    private val expandableButtonAnimationRotation = 180f

    private var isListExpanded = true
    private lateinit var expandableButtonAnimation: AnimatorSet


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_see_trip, container, false)

        val recycleView : RecyclerView? = view?.rootView?.rv2

        val intent: Intent? = activity?.intent

        val trips: Trips = intent?.getSerializableExtra("trip") as Trips

        view.rootView.locationNameText.text = trips?.name

        view.rootView.expected_points.text = trips?.listOfPOI?.size.toString()

        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val startDateTrip = trips?.dateStart?.format(formatter)
        val endDateTrip = trips?.dateEnd?.format(formatter)

        if(startDateTrip == null || endDateTrip == null){
            view.rootView.displayDateText.text = "No Date"
        }
        else {
            view.rootView.displayDateText.text =
                startDateTrip.toString() + " - " + endDateTrip.toString()
        }

        view.rootView.backArrowButton2.setOnClickListener {
            activity?.finish()
        }
        val listOfPOI: MutableList<Trips.Trip> = mutableListOf<Trips.Trip>()

        trips.listOfPOI.forEach {
            listOfPOI.add(it)
        }
        recyclerViewAdapter = POIAdapter(listOfPOI, requireContext())
        recycleView?.adapter = recyclerViewAdapter
        recycleView?.layoutManager = LinearLayoutManager(context)

        val expandableButton = view.rootView.expandableButton

        setExpandableAnimation(expandableButton)

        expandableButton.setOnClickListener {
            if (recycleView != null) {
                handleExpandCollapse(recycleView)
            }
        }

        view.rootView.seeOnMapButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigationFromSavedTripToMaps)
        }

        if(trips.listOfPOI.isEmpty()){
            view.rootView.seeOnMapButton.isEnabled = false
        }

        view.rootView.updateTripButton.setOnClickListener {
            val intent = Intent(context, POIScreenActivity::class.java)
            intent.putExtra("place", trips.name)
            intent.putExtra("database", "Update")
            intent.putExtra("trip", trips)
            startActivity(intent)
            activity?.finish()
        }


        return view
    }


    private fun handleExpandCollapse(tripsList: RecyclerView) {
        expandCollapse(tripsList)
    }

    private fun setExpandableAnimation(button: View) {
        val rotate = ObjectAnimator
            .ofFloat(button, View.ROTATION, expandableButtonAnimationRotation)
            .setDuration(expandableButtonAnimationDuration)

        rotate.interpolator = AccelerateDecelerateInterpolator()
        rotate.repeatMode = ObjectAnimator.REVERSE

        expandableButtonAnimation = AnimatorSet()
        expandableButtonAnimation.playSequentially(
            rotate
        )
    }

    private fun expandCollapse(tripsList: RecyclerView) {
        // TODO: Animate expanding/collapsing

        isListExpanded = !isListExpanded

        if (isListExpanded) {
            tripsList.visibility = View.VISIBLE
            if (android.os.Build.VERSION.SDK_INT >= 26)
                expandableButtonAnimation.reverse()
        }
        else {
            tripsList.visibility = View.GONE
            expandableButtonAnimation.start()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)

    }



}