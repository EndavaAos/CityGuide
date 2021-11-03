package com.example.cityguide.data.repository

import com.example.cityguide.data.db.entity.Trips
import io.reactivex.rxjava3.core.Observable

interface TripRepository {

    fun getActiveTrips() : Observable<List<Trips>>
    fun getUpcomingTrips() : Observable<List<Trips>>
    fun getCompletedTrips() : Observable<List<Trips>>
    suspend fun insertTrips(trips: Trips)
    suspend fun deleteTrip(trip: Trips)
}