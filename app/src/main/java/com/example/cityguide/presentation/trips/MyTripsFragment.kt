package com.example.cityguide.presentation.trips

import android.content.Context
import android.os.Bundle
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

        initializeTripListsPreviews()
    }

    private fun initializeTripListsPreviews() {
        val parent = this
        parentFragmentManager.commit {
            val activeTripsFragment = ActiveTripsFragment()
            activeTripsFragment.parent = parent
            val upcomingTripsFragment = UpcomingTripsFragment()
            upcomingTripsFragment.parent = parent
            val completedTripsFragment = CompletedTripsFragment()
            completedTripsFragment.parent = parent

            add(R.id.trips_active, activeTripsFragment)
            add(R.id.trips_upcoming, upcomingTripsFragment)
            add(R.id.trips_completed, completedTripsFragment)

            setReorderingAllowed(true)
        }
    }
}
