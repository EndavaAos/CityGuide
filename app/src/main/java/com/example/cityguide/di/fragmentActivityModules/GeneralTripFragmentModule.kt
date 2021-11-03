package com.example.cityguide.di.fragmentActivityModules

import com.example.cityguide.presentation.trips.tripSegment.ActiveTripsFragment
import com.example.cityguide.presentation.trips.tripSegment.CompletedTripsFragment
import com.example.cityguide.presentation.trips.tripSegment.UpcomingTripsFragment
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