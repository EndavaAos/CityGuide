package com.example.cityguide.data.repository

import com.example.cityguide.data.db.entity.NotificationConfig
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface UpcomingNotificationTimeRepository {

    fun getUpcomingNotificationTime() : Observable<NotificationConfig>
    fun updateUpcomingNotificationTime(hour: Int, minute: Int, isCheckedUpcoming: Boolean, isCheckedCommercial: Boolean) : Completable
}