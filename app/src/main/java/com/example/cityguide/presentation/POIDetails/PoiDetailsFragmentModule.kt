package com.example.cityguide.presentation.POIDetails

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PoiDetailsFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributePoiDetailsFragmentModule(): PoiDetailsFragment
}