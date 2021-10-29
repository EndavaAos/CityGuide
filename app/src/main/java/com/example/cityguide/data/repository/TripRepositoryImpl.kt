package com.example.cityguide.data.repository

import com.example.cityguide.data.db.TripDatabase
import com.example.cityguide.data.db.entity.Trip
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class TripRepositoryImpl @Inject constructor(
    db: TripDatabase
) : TripRepository {

    private val dao = db.tripDao()

    override fun getActiveTrips(): Observable<List<Trip>> =
        dao.getActiveTrips().subscribeOn(
        Schedulers.io())
        .observeOn(Schedulers.io())
    override fun getUpcomingTrips() : Observable<List<Trip>> =
        dao.getUpcomingTrips().subscribeOn(
            Schedulers.io())
            .observeOn(Schedulers.io())
    override fun getCompletedTrips() : Observable<List<Trip>> =
        dao.getCompletedTrips().subscribeOn(
            Schedulers.io())
            .observeOn(Schedulers.io())
}