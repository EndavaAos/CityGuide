package com.example.cityguide.presentation.trips.tripSegment

import com.example.cityguide.data.db.entity.Trips
import com.example.cityguide.data.repository.TripRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class ActiveTripsViewModel @Inject constructor(
    private val repository: TripRepository
) : GeneralTripViewModel(repository) {

    override val data: Observable<List<Trips>>
        get() = repository.getActiveTrips()
}