package com.example.cityguide.data.repository

import com.example.cityguide.data.db.entity.Trip
import kotlinx.coroutines.flow.Flow

interface TripRepository {

    fun getActiveTrips() : Flow<List<Trip>>
    fun getUpcomingTrips() : Flow<List<Trip>>
    fun getCompletedTrips() : Flow<List<Trip>>
}