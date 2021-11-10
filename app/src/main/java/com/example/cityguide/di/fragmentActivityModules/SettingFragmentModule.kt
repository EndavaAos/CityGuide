package com.example.cityguide.di.fragmentActivityModules

import com.example.cityguide.presentation.settings.SettingsFragment
import com.example.cityguide.presentation.trips.MyTripsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SettingFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeSettingsFragmentModule() : SettingsFragment

}