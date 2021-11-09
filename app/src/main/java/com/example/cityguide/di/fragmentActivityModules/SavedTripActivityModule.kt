package com.example.cityguide.di.fragmentActivityModules

import com.example.cityguide.presentation.SavedTrip.SeeTripActivity
import com.example.cityguide.presentation.SavedTrip.SeeTripFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SavedTripActivityModule {

    @ContributesAndroidInjector
    abstract fun contributePOIScreenFragmentModule() : SeeTripActivity

}