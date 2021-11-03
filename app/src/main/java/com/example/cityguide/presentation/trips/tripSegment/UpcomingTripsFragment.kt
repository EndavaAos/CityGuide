package com.example.cityguide.presentation.trips.tripSegment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.example.cityguide.R
import com.example.cityguide.data.db.entity.Trips
import javax.inject.Inject

class UpcomingTripsFragment : GeneralTripFragment() {

    @Inject
    lateinit var viewModel: UpcomingTripsViewModel

    override val title: String
        get() = "Upcoming trips"

    override val observableData: LiveData<List<Trips>>
        get() = viewModel.trips

    override val errorScreen: Fragment
        get() = Fragment(R.layout.trips_fragment_no_upcoming_trips)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getTrips()
    }
}