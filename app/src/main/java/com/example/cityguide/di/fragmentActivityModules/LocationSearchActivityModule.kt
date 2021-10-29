package com.example.cityguide.di.fragmentActivityModules

import com.example.cityguide.presentation.location.LocationSearchActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LocationSearchActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeLocationShareActivity(): LocationSearchActivity
}