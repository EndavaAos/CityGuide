package com.example.cityguide.di

import com.example.cityguide.presentation.trips.UpcomingTripsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UpcomingTripsFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeUpcomingTripsFragmentModule() : UpcomingTripsFragment
}