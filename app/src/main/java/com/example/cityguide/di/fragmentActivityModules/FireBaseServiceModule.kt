package com.example.cityguide.di.fragmentActivityModules

import com.example.cityguide.data.services.PushNotificationService
import com.example.cityguide.presentation.SavedTrip.SeeTripActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FireBaseServiceModule {

    @ContributesAndroidInjector
    abstract fun contributePOIScreenFragmentModule() : PushNotificationService
}