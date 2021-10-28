package com.example.cityguide.di

import com.example.cityguide.CityGuideApp
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        AndroidInjectionModule::class,
        LocationSearchActivityModule::class,
        LocationSearchFragmentModule::class,
        ActiveTripsFragmentModule::class,
        CompletedTripsFragmentModule::class,
        UpcomingTripsFragmentModule::class,
        MainActivityModule::class,
        MyTripsFragmentModule::class,
        AppModule::class,
        DatabaseModule::class,
    ]
)
@Singleton
interface AppComponent {

    fun inject(application: CityGuideApp)
}