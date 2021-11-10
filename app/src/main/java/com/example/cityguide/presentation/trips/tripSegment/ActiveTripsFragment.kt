package com.example.cityguide.presentation.trips.tripSegment

import androidx.fragment.app.Fragment
import com.example.cityguide.R
import javax.inject.Inject

class ActiveTripsFragment : GeneralTripFragment() {

    @Inject
    override lateinit var viewModel: ActiveTripsViewModel

    override val title: String
        get() = "Active trips"

    override val errorScreen: Fragment
        get() = Fragment(R.layout.trips_fragment_no_active_trips)
}