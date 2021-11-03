package com.example.cityguide.presentation.POIDetails

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PoiDetailsFragment2Module {

    @ContributesAndroidInjector
    abstract fun contributePoiDetailsFragmentModule(): PoiDetailsFragment2
}