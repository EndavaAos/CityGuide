package com.example.cityguide.data.repository

import com.example.cityguide.data.db.TripDatabase
import com.example.cityguide.data.db.entity.UpcomingNotificationTime
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class UpcomingNotificationTimeRepositoryImpl @Inject constructor(
    db: TripDatabase
) : UpcomingNotificationTimeRepository {

    private val dao = db.upcomingNotificationTimeDao()

    override fun getUpcomingNotificationTime(): Observable<UpcomingNotificationTime> =
        dao.getUpcomingNotificationTime()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())

    override fun updateUpcomingNotificationTime(hour: Int, minute: Int): Completable =
        dao.insertUpcomingNotificationTime(
            UpcomingNotificationTime(hour = hour, minutes = minute)
        )
}