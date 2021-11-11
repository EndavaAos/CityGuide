package com.example.cityguide.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.cityguide.data.db.dao.TripDao
import com.example.cityguide.data.db.dao.UpcomingNotificationTimeDao
import com.example.cityguide.util.Converters
import com.example.cityguide.data.db.entity.Trips
import com.example.cityguide.data.db.entity.NotificationConfig
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [
        Trips::class,
        NotificationConfig::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class TripDatabase : RoomDatabase() {

    abstract fun tripDao(): TripDao
    abstract fun upcomingNotificationTimeDao(): UpcomingNotificationTimeDao

    class Callback @Inject constructor(
        private val database: Provider<TripDatabase>
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao = database.get().upcomingNotificationTimeDao()

            dao.insertUpcomingNotificationTime(NotificationConfig())
                .subscribeOn(Schedulers.io())
                .subscribe()
        }
    }
}