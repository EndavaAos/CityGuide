package com.example.cityguide

import android.app.Application
import com.example.cityguide.di.AppModule
import com.example.cityguide.di.DaggerAppComponent
import com.example.cityguide.di.DatabaseModule
import com.google.firebase.messaging.FirebaseMessaging
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class CityGuideApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .databaseModule(DatabaseModule())
            .build()
            .inject(this)

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if(it.isComplete){
                println("NEW TOKEN " + it.result.toString())
            }
        }
    }

    override fun androidInjector(): AndroidInjector<Any> = injector


}