package com.example.cityguide.presentation.trips.tripSegment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.example.cityguide.R
import com.example.cityguide.data.db.entity.Trip
import javax.inject.Inject

class ActiveTripsFragment : GeneralTripFragment() {

    @Inject
    lateinit var viewModel: ActiveTripsViewModel

    override val title: String
        get() = "Active trips"

    override val observableData: LiveData<List<Trip>>
        get() = viewModel.trips

    override val errorScreen: Fragment
        get() = Fragment(R.layout.trips_fragment_no_active_trips)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getTrips()
    }
}