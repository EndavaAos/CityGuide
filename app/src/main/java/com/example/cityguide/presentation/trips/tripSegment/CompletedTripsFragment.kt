package com.example.cityguide.presentation.trips.tripSegment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.cityguide.R
import javax.inject.Inject

class CompletedTripsFragment : GeneralTripFragment() {

    @Inject
    override lateinit var viewModel: CompletedTripsViewModel

    override val title: String
        get() = "Completed trips"

    override val errorScreen: Fragment
        get() = Fragment(R.layout.trips_fragment_no_completed_trips)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getTrips()
    }
}