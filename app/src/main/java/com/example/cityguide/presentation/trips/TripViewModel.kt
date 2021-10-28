package com.example.cityguide.presentation.trips

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.cityguide.data.repository.TripRepository
import javax.inject.Inject

class TripViewModel @Inject constructor(
    repository: TripRepository
) : ViewModel() {

    val activeTrips = repository.getActiveTrips().asLiveData()
    val upcomingTrips = repository.getUpcomingTrips().asLiveData()
    val completedTrips = repository.getCompletedTrips().asLiveData()
}