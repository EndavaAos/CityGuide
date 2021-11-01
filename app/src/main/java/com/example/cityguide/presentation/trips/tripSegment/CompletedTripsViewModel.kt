package com.example.cityguide.presentation.trips.tripSegment

import com.example.cityguide.data.db.entity.Trips
import com.example.cityguide.data.repository.TripRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class CompletedTripsViewModel @Inject constructor(
    private val repository: TripRepository
) : GeneralTripViewModel() {

    override val data: Observable<List<Trips>>
        get() = repository.getCompletedTrips()
}