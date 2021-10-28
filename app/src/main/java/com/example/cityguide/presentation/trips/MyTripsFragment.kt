package com.example.cityguide.presentation.trips

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.cityguide.R
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class MyTripsFragment : Fragment(R.layout.fragment_my_trips) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeTripListsPreviews()
    }

    private fun initializeTripListsPreviews() {
        parentFragmentManager.commit {
            val activeTripsFragment = ActiveTripsFragment()
            val upcomingTripsFragment = UpcomingTripsFragment()
            val completedTripsFragment = CompletedTripsFragment()

            add(R.id.trips_active, activeTripsFragment)
            add(R.id.trips_upcoming, upcomingTripsFragment)
            add(R.id.trips_completed, completedTripsFragment)

            setReorderingAllowed(true)
        }
    }
}
