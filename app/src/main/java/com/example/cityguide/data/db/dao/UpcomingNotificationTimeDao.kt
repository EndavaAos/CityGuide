package com.example.cityguide.data.db.dao

import androidx.room.*
import com.example.cityguide.data.db.entity.NotificationConfig
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

@Dao
interface UpcomingNotificationTimeDao {

    @Transaction
    @Query("SELECT * FROM NotificationConfig LIMIT 1")
    fun getUpcomingNotificationTime(): Observable<NotificationConfig>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUpcomingNotificationTime(notificationConfig: NotificationConfig) : Completable
}