package com.example.cityguide.di

import com.example.cityguide.CityGuideApp
import com.example.cityguide.data.services.UpcomingNotification
import com.example.cityguide.di.fragmentActivityModules.*
import com.example.cityguide.presentation.POIDetails.PoiDetailsFragment2Module
import com.example.cityguide.presentation.POIDetails.PoiDetailsFragmentModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        AndroidInjectionModule::class,
        LocationSearchActivityModule::class,
        LocationSearchFragmentModule::class,
        GeneralTripFragmentModule::class,
        MainActivityModule::class,
        MyTripsFragmentModule::class,
        AppModule::class,
        DatabaseModule::class,
        POIScreenFragmentModule::class,
        MakeTripFragmentModule::class,
        PoiDetailsFragmentModule::class,
        PoiDetailsFragment2Module::class,
        SavedTripFragmentModule::class,
        SavedTripActivityModule::class,
        SettingFragmentModule::class
    ]

)
@Singleton
interface AppComponent {

    fun inject(application: CityGuideApp)

    fun inject2(worker: UpcomingNotification)
}