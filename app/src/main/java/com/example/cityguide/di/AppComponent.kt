package com.example.cityguide.di

import com.example.cityguide.CityGuideApp
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        LocationSearchActivityModule::class,
        AndroidInjectionModule::class,
        LocationSearchFragmentModule::class,
        AppModule::class,
        DatabaseModule::class,
    ]
)
@Singleton
interface AppComponent {

    fun inject(application: CityGuideApp)
}