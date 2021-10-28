package com.example.cityguide.presentation.trips

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.example.cityguide.R
import com.example.cityguide.data.db.entity.Trip
import javax.inject.Inject

class CompletedTripsFragment : GeneralTripFragment() {

    @Inject
    lateinit var viewModel: CompletedTripsViewModel

    override val title: String
        get() = "Completed trips"

    override val observableData: LiveData<List<Trip>>
        get() = viewModel.trips

    override val errorScreen: Fragment
        get() = Fragment(R.layout.no_completed_trips)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getTrips()
    }
}