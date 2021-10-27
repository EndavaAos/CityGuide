package com.example.cityguide.di

import com.example.cityguide.presentation.location.LocationSearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LocationSearchFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeLocationShareFragmentModule() : LocationSearchFragment
}