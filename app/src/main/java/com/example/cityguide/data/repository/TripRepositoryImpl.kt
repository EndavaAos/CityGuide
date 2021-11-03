package com.example.cityguide.data.repository

import com.example.cityguide.data.db.TripDatabase
import com.example.cityguide.data.db.entity.Trips
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.time.LocalDate
import javax.inject.Inject

class TripRepositoryImpl @Inject constructor(
    db: TripDatabase
) : TripRepository {

    private val dao = db.tripDao()

    override fun getActiveTrips(): Observable<List<Trips>> =
        dao.getActiveTrips(LocalDate.now().toEpochDay()).subscribeOn(
        Schedulers.io())
        .observeOn(Schedulers.io())
    override fun getUpcomingTrips() : Observable<List<Trips>> =
        dao.getUpcomingTrips(LocalDate.now().toEpochDay()).subscribeOn(
            Schedulers.io())
            .observeOn(Schedulers.io())
    override fun getCompletedTrips() : Observable<List<Trips>> =
        dao.getCompletedTrips(LocalDate.now().toEpochDay()).subscribeOn(
            Schedulers.io())
            .observeOn(Schedulers.io())

    override suspend fun insertTrips(trips: Trips){
        dao.insertTrips(trips)
    }
}