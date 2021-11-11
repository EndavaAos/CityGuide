package com.example.cityguide.data.repository

import com.example.cityguide.data.db.entity.Trips
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface TripRepository {

    fun getActiveTrips() : Observable<List<Trips>>
    fun getUpcomingTrips() : Observable<List<Trips>>
    fun getCompletedTrips() : Observable<List<Trips>>
    fun getTripById(id: Int) : Observable<Trips>
    fun insertTrips(trips: Trips) : Completable
    fun updateTrips(trips: Trips) : Completable
    fun deleteTrip(trips: Trips) : Completable
    fun getAllTrips(): Observable<List<Trips>>
}