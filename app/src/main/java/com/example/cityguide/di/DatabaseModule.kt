package com.example.cityguide.di

import android.content.Context
import androidx.room.Room
import com.example.cityguide.data.db.TripDatabase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class DatabaseModule {

//    @Provides
//    @Singleton
//    fun providesDatabase(context: Context) = Room.databaseBuilder(
//        context,
//        TripDatabase::class.java,
//        "trip_database"
//    ).fallbackToDestructiveMigration()
//        .build()
//
//    @Provides
//    @Singleton
//    fun providesTripDao(db: TripDatabase) =  db.tripDao()
//
//    @ApplicationScope
//    @Provides
//    @Singleton
//    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DatabaseInfo

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScope