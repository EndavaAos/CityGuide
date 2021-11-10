package com.example.cityguide.data.db.dao

import androidx.room.*
import com.example.cityguide.data.db.entity.UpcomingNotificationTime
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

@Dao
interface UpcomingNotificationTimeDao {

    @Transaction
    @Query("SELECT * FROM upcomingnotificationtime LIMIT 1")
    fun getUpcomingNotificationTime(): Observable<UpcomingNotificationTime>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUpcomingNotificationTime(upcomingNotificationTime: UpcomingNotificationTime) : Completable
}