package com.example.cityguide.data.repository

import com.example.cityguide.data.db.TripDatabase
import com.example.cityguide.data.db.entity.NotificationConfig
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class UpcomingNotificationTimeRepositoryImpl @Inject constructor(
    db: TripDatabase
) : UpcomingNotificationTimeRepository {

    private val dao = db.upcomingNotificationTimeDao()

    override fun getUpcomingNotificationTime(): Observable<NotificationConfig> =
        dao.getUpcomingNotificationTime()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())

    override fun updateUpcomingNotificationTime(hour: Int, minute: Int, isCheckedUpcoming: Boolean, isCheckedCommercial: Boolean): Completable =
        dao.insertUpcomingNotificationTime(
            NotificationConfig(hour = hour, minutes = minute, isCommercialActive = isCheckedCommercial, isUpcomingActive = isCheckedUpcoming)
        )

}