package com.example.cityguide.di

import com.example.cityguide.CityGuideApp
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
        PoiDetailsFragmentModule::class
    ]

)
@Singleton
interface AppComponent {

    fun inject(application: CityGuideApp)
}