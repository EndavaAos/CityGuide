package com.example.cityguide.data.db.dao

import androidx.room.*
import com.example.cityguide.data.db.entity.Trips
import io.reactivex.rxjava3.core.Observable

@Dao
interface TripDao {

    @Transaction
    @Query("SELECT * FROM trips")
    fun getAllTrips(): Observable<List<Trips>>

    @Transaction
    @Query("SELECT * FROM trips WHERE trips.dateStart <= :currentDate AND :currentDate <= trips.dateEnd ORDER BY trips.dateStart")
    fun getActiveTrips(currentDate: Long): Observable<List<Trips>>

    @Transaction
    @Query("SELECT * FROM trips WHERE trips.dateStart IS NULL OR trips.dateEnd IS NULL OR trips.dateStart > :currentDate ORDER BY trips.dateStart")
    fun getUpcomingTrips(currentDate: Long): Observable<List<Trips>>

    @Transaction
    @Query("SELECT * FROM trips WHERE :currentDate > trips.dateEnd ORDER BY trips.dateStart")
    fun getCompletedTrips(currentDate: Long): Observable<List<Trips>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrips(trips: Trips)

    @Update
    suspend fun updateTrip(trips: Trips)

    @Delete
    suspend fun deleteTrip(trips: Trips)

    @Transaction
    @Query("DELETE FROM trips")
    suspend fun deleteAllTrips()
}