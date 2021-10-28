package com.example.cityguide.di

import com.example.cityguide.presentation.trips.ActiveTripsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActiveTripsFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeActiveTripsFragmentModule() : ActiveTripsFragment
}