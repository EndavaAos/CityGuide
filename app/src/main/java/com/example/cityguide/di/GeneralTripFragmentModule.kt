package com.example.cityguide.di

import com.example.cityguide.presentation.trips.ActiveTripsFragment
import com.example.cityguide.presentation.trips.CompletedTripsFragment
import com.example.cityguide.presentation.trips.UpcomingTripsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class GeneralTripFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeActiveTripsFragmentModule() : ActiveTripsFragment

    @ContributesAndroidInjector
    abstract fun contributeUpcomingTripsFragmentModule() : UpcomingTripsFragment

    @ContributesAndroidInjector
    abstract fun contributeCompletedTripsFragmentModule() : CompletedTripsFragment
}