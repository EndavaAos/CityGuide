package com.example.cityguide.di

import android.content.Context
import com.example.cityguide.data.network.LocationApi
import com.example.cityguide.data.network.RemoteDataSource
import com.example.cityguide.data.repository.LocationRepository
import com.example.cityguide.data.repository.LocationRepositoryImpl
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
        return context;
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
    fun providesGson(): Gson = Gson()
}