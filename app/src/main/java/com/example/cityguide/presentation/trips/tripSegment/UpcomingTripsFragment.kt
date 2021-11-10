package com.example.cityguide.presentation.trips.tripSegment

import androidx.fragment.app.Fragment
import com.example.cityguide.R
import javax.inject.Inject

class UpcomingTripsFragment : GeneralTripFragment() {

    @Inject
    override lateinit var viewModel: UpcomingTripsViewModel

    override val title: String
        get() = "Upcoming trips"

    override val errorScreen: Fragment
        get() = Fragment(R.layout.trips_fragment_no_upcoming_trips)
}