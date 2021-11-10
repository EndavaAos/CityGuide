package com.example.cityguide.di

import android.content.Context
import androidx.room.Room
import com.example.cityguide.data.db.TripDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(context: Context, callBack: TripDatabase.Callback) = Room.databaseBuilder(
        context,
        TripDatabase::class.java,
        "trip_database"
    ).fallbackToDestructiveMigration()
        .addCallback(callBack)
        .build()

    @Provides
    @Singleton
    fun providesTripDao(db: TripDatabase) =  db.tripDao()

    @Provides
    @Singleton
    fun providesUpcomingNotificationTimeDao(db: TripDatabase) =  db.upcomingNotificationTimeDao()
}