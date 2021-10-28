package com.example.cityguide.data.db.dao

import androidx.room.*
import com.example.cityguide.data.db.entity.Trip
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDao {

    @Transaction
    @Query("SELECT * FROM trip")
    fun getAllTrips(): Observable<List<Trip>>

    @Transaction
    @Query("SELECT * FROM trip WHERE trip.dateStart <= (SELECT strftime('%s', 'now')) AND (SELECT strftime('%s', 'now')) <= trip.dateEnd")
    fun getActiveTrips(): Observable<List<Trip>>

    @Transaction
    @Query("SELECT * FROM trip WHERE trip.dateStart IS NULL OR trip.dateEnd IS NULL OR trip.dateStart >= (SELECT strftime('%s', 'now'))")
    fun getUpcomingTrips(): Observable<List<Trip>>

    @Transaction
    @Query("SELECT * FROM trip WHERE (SELECT strftime('%s', 'now')) >= trip.dateEnd")
    fun getCompletedTrips(): Observable<List<Trip>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrips(trips: List<Trip>)

    @Update
    suspend fun updateTrip(trip: Trip)

    @Delete
    suspend fun deleteTrip(trip: Trip)

    @Transaction
    @Query("DELETE FROM trip")
    suspend fun deleteAllTrips()
}