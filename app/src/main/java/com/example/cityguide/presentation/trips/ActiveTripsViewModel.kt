package com.example.cityguide.presentation.trips

import com.example.cityguide.data.db.entity.Trip
import com.example.cityguide.data.repository.TripRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class ActiveTripsViewModel @Inject constructor(
    private val repository: TripRepository
) : TripViewModel() {

    override val data: Observable<List<Trip>>
        get() = repository.getActiveTrips()
}