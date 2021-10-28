package com.example.cityguide.di

import com.example.cityguide.presentation.trips.MyTripsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MyTripsFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeMyTripsFragmentModule() : MyTripsFragment
}