package com.example.cityguide.di

import android.content.Context
import androidx.room.Room
import com.example.cityguide.data.db.TripDatabase
import com.example.cityguide.data.db.dao.TripDao
import com.example.cityguide.data.network.LocationApi
import com.example.cityguide.data.network.RemoteDataSource
import com.example.cityguide.data.repository.LocationRepository
import com.example.cityguide.data.repository.LocationRepositoryImpl
import com.example.cityguide.data.repository.TripRepository
import com.example.cityguide.data.repository.TripRepositoryImpl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(
    private val context: Context
) {

    @Provides
    @Singleton
    fun providesDatabase(context: Context) : TripDatabase = Room.databaseBuilder(
        context,
        TripDatabase::class.java,
        "trip_database"
    ).fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun providesTripDao(db: TripDatabase) : TripDao =  db.tripDao()

    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }

    @Singleton
    @Provides
    fun providesLocationApi(remoteDataSource: RemoteDataSource, context: Context): LocationApi {
        return remoteDataSource.buildApi(LocationApi::class.java, context)

    }

    @Singleton
    @Provides
    fun providesLocationRepository(locationApi: LocationApi): LocationRepository {
        return LocationRepositoryImpl(locationApi)
    }

    @Singleton
    @Provides
    fun providesTripRepository(tripDatabase: TripDatabase): TripRepository
        = TripRepositoryImpl(tripDatabase)

    @Singleton
    @Provides
    fun providesGson(): Gson = Gson()
}