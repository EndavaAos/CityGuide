package com.example.cityguide.data.db.dao

import androidx.room.*
import com.example.cityguide.data.db.entity.Trips
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface TripDao {

    @Transaction
    @Query("SELECT * FROM trips")
    fun getAllTrips(): Observable<List<Trips>>

    @Transaction
    @Query("SELECT * FROM trips WHERE trips.dateStart <= :currentDate AND :currentDate <= trips.dateEnd")
    fun getActiveTrips(currentDate: Long): Observable<List<Trips>>

    @Transaction
    @Query("SELECT * FROM trips WHERE trips.dateStart IS NULL OR trips.dateEnd IS NULL OR trips.dateStart > :currentDate")
    fun getUpcomingTrips(currentDate: Long): Observable<List<Trips>>

    @Transaction
    @Query("SELECT * FROM trips WHERE :currentDate > trips.dateEnd")
    fun getCompletedTrips(currentDate: Long): Observable<List<Trips>>

    @Transaction
    @Query("SELECT * FROM trips WHERE trips.id =:id")
    fun getTripById(id: Int): Observable<Trips>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrips(trips: Trips) : Completable

    @Update
    fun updateTrip(trips: Trips) : Completable

    @Delete
    suspend fun deleteTrip(trips: Trips)

    @Transaction
    @Query("DELETE FROM trips")
    suspend fun deleteAllTrips()

}