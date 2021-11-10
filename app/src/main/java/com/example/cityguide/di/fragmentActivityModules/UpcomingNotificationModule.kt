package com.example.cityguide.di.fragmentActivityModules

import com.example.cityguide.data.services.UpcomingNotification
import com.example.cityguide.presentation.SavedTrip.SeeTripFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UpcomingNotificationModule {

    @ContributesAndroidInjector
    abstract fun contributeUpcomingNotificationModule() : UpcomingNotification

}