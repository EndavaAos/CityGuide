package com.example.cityguide.di

import com.example.cityguide.CityGuideApp
import com.example.cityguide.presentation.POIDetails.PoiDetailsFragmentModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        LocationSearchActivityModule::class,
        AndroidInjectionModule::class,
        LocationSearchFragmentModule::class,
        POIScreenFragmentModule::class,
        AppModule::class,
        PoiDetailsFragmentModule::class]
)
@Singleton
interface AppComponent {

    fun inject(application: CityGuideApp)
}