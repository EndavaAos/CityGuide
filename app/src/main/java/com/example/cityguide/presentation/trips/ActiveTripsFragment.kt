package com.example.cityguide.presentation.trips

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
        get() = Fragment(R.layout.no_active_trips)
}