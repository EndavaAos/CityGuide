package com.example.cityguide.data.repository

import com.example.cityguide.data.db.entity.Trip
import io.reactivex.rxjava3.core.Observable

interface TripRepository {

    fun getActiveTrips() : Observable<List<Trip>>
    fun getUpcomingTrips() : Observable<List<Trip>>
    fun getCompletedTrips() : Observable<List<Trip>>
}