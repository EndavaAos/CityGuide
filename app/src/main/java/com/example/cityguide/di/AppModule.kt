package com.example.cityguide.di

import android.content.Context
import com.example.cityguide.data.db.TripDatabase
import com.example.cityguide.data.network.LocationApi
import com.example.cityguide.data.network.RemoteDataSource
import com.example.cityguide.data.repository.LocationRepository
import com.example.cityguide.data.repository.LocationRepositoryImpl
import com.example.cityguide.presentation.POIsScreen.RecyclerViewAdapter
import com.example.cityguide.data.repository.TripRepository
import com.example.cityguide.data.repository.TripRepositoryImpl
import com.example.cityguide.presentation.trips.ActiveTripsViewModel
import com.example.cityguide.presentation.trips.CompletedTripsViewModel
import com.example.cityguide.presentation.trips.UpcomingTripsViewModel
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

    @Singleton
    @Provides
    fun providesLocationApi(remoteDataSource: RemoteDataSource, context: Context): LocationApi {
        return remoteDataSource.buildApi(LocationApi::class.java, context)

    }

    @Singleton
    @Provides
    fun providesRecyclerViewAdapter(context: Context): RecyclerViewAdapter {
        return RecyclerViewAdapter(context)
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