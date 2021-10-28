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

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DatabaseInfo

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScope