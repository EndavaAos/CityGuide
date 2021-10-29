package com.example.cityguide.di

import com.example.cityguide.presentation.POIsScreen.POIScreenFragment
import com.example.cityguide.presentation.makeATrip.MakeTripFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MakeTripFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeMakeTripFragmentModule() : MakeTripFragment
}