package com.example.cityguide.di.fragmentActivityModules

import com.example.cityguide.presentation.POIsScreen.POIScreenFragment
import com.example.cityguide.presentation.SavedTrip.SeeTripFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SavedTripFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributePOIScreenFragmentModule() : SeeTripFragment
}