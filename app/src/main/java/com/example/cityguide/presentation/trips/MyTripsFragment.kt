package com.example.cityguide.presentation.trips

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.cityguide.R
import com.example.cityguide.presentation.trips.tripSegment.ActiveTripsFragment
import com.example.cityguide.presentation.trips.tripSegment.CompletedTripsFragment
import com.example.cityguide.presentation.trips.tripSegment.UpcomingTripsFragment
import dagger.android.support.AndroidSupportInjection

class MyTripsFragment : Fragment(R.layout.trips_fragment_screen) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeTripListsPreviews(view)
    }

    private fun initializeTripListsPreviews(view: View) {
        parentFragmentManager.commit {
            val activeTripsFragment = ActiveTripsFragment()
            activeTripsFragment.parent = view
            val upcomingTripsFragment = UpcomingTripsFragment()
            upcomingTripsFragment.parent = view
            val completedTripsFragment = CompletedTripsFragment()
            completedTripsFragment.parent = view

            replace(R.id.trips_active, activeTripsFragment)
            replace(R.id.trips_upcoming, upcomingTripsFragment)
            replace(R.id.trips_completed, completedTripsFragment)

            setReorderingAllowed(true)
        }
    }
}
