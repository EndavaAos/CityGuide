package com.example.cityguide.di

import com.example.cityguide.presentation.POIsScreen.POIScreenFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class POIScreenFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributePOIScreenFragmentModule() : POIScreenFragment
}