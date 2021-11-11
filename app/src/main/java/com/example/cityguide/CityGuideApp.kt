package com.example.cityguide

import android.app.Application
import androidx.work.Configuration
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.example.cityguide.data.services.UpcomingNotification
import com.example.cityguide.data.services.UpcomingNotificationFactory
import com.example.cityguide.di.AppModule
import com.example.cityguide.di.DaggerAppComponent
import com.example.cityguide.di.DatabaseModule
import com.google.firebase.messaging.FirebaseMessaging
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CityGuideApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var workerFactory: WorkerFactory

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

        val config = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

        WorkManager.initialize(applicationContext, config)
    }

    override fun androidInjector(): AndroidInjector<Any> = injector


}