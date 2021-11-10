package com.example.cityguide.data.repository

import com.example.cityguide.data.db.entity.UpcomingNotificationTime
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface UpcomingNotificationTimeRepository {

    fun getUpcomingNotificationTime() : Observable<UpcomingNotificationTime>
    fun updateUpcomingNotificationTime(hour: Int, minute: Int) : Completable
}