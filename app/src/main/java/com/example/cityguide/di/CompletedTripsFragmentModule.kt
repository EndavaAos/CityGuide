package com.example.cityguide.di

import com.example.cityguide.presentation.trips.CompletedTripsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CompletedTripsFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeCompletedTripsFragmentModule() : CompletedTripsFragment
}