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
    @Query("SELECT * FROM trips WHERE dateStart <= :currentDate AND :currentDate <= dateEnd ORDER BY dateStart, dateEnd, country")
    fun getActiveTrips(currentDate: Long): Observable<List<Trips>>

    @Transaction
    @Query("SELECT * FROM trips WHERE dateStart IS NULL OR dateEnd IS NULL OR dateStart > :currentDate ORDER BY dateStart ISNULL, dateStart, dateEnd, country")
    fun getUpcomingTrips(currentDate: Long): Observable<List<Trips>>

    @Transaction
    @Query("SELECT * FROM trips WHERE :currentDate > dateEnd ORDER BY dateStart, dateEnd, country")
    fun getCompletedTrips(currentDate: Long): Observable<List<Trips>>

    @Transaction
    @Query("SELECT * FROM trips WHERE trips.id =:id")
    fun getTripById(id: Int): Observable<Trips>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrips(trips: Trips) : Completable

    @Update
    fun updateTrip(trips: Trips) : Completable

    @Delete
    fun deleteTrip(trips: Trips) : Completable

    @Transaction
    @Query("DELETE FROM trips")
    suspend fun deleteAllTrips()

}