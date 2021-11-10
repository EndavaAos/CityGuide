package com.example.cityguide.di

import android.content.Context
import androidx.work.WorkerFactory
import com.example.cityguide.data.db.TripDatabase
import com.example.cityguide.data.network.LocationApi
import com.example.cityguide.data.network.RemoteDataSource
import com.example.cityguide.data.repository.*
import com.example.cityguide.data.services.UpcomingNotificationFactory
import com.example.cityguide.presentation.trips.tripSegment.ActiveTripsViewModel
import com.example.cityguide.presentation.trips.tripSegment.CompletedTripsViewModel
import com.example.cityguide.presentation.trips.tripSegment.UpcomingTripsViewModel
import com.example.cityguide.presentation.POIsScreen.RecyclerViewAdapter
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
    fun provideContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun workerFactory(tripRepository: TripRepository): WorkerFactory{
        return UpcomingNotificationFactory(tripRepository)
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
    fun providesUpcomingNotificationTimeRepository(tripDatabase: TripDatabase): UpcomingNotificationTimeRepository{
        return UpcomingNotificationTimeRepositoryImpl(tripDatabase)
    }

    @Singleton
    @Provides
    fun providesTripRepository(tripDatabase: TripDatabase): TripRepository
        = TripRepositoryImpl(tripDatabase)

    @Singleton
    @Provides
    fun providesGson(): Gson = Gson()

    @Singleton
    @Provides
    fun providesActiveTripsViewModel(repo: TripRepository): ActiveTripsViewModel
        = ActiveTripsViewModel(repo)

    @Singleton
    @Provides
    fun providesUpcomingTripsViewModel(repo: TripRepository): UpcomingTripsViewModel
        = UpcomingTripsViewModel(repo)

    @Singleton
    @Provides
    fun providesCompletedTripsViewModel(repo: TripRepository): CompletedTripsViewModel
        = CompletedTripsViewModel(repo)
}