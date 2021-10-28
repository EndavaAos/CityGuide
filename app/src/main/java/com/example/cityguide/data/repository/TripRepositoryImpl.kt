package com.example.cityguide.data.repository

import com.example.cityguide.data.db.TripDatabase
import javax.inject.Inject

class TripRepositoryImpl @Inject constructor(
    // also api
    db: TripDatabase
) : TripRepository {

    private val dao = db.tripDao()

    override fun getActiveTrips() = dao.getActiveTrips()
    override fun getUpcomingTrips() = dao.getUpcomingTrips()
    override fun getCompletedTrips() = dao.getCompletedTrips()
}