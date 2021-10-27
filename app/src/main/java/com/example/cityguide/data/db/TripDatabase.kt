package com.example.cityguide.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cityguide.data.db.dao.TripDao
import com.example.cityguide.data.db.entity.Trip
import com.example.cityguide.util.Converters

@Database(
    entities = [
        Trip::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class TripDatabase : RoomDatabase() {

    abstract fun tripDao(): TripDao
}