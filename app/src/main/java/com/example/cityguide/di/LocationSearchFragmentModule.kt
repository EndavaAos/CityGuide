package com.example.cityguide.di

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LocationSearchFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeLocationShareFragmentModule() : LocationSearchFragmentModule
}