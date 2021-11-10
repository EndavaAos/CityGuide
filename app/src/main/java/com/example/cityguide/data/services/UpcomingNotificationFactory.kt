package com.example.cityguide.data.services

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.cityguide.data.repository.TripRepository

class UpcomingNotificationFactory(private val tripRepository: TripRepository): WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        val workerClass = Class.forName(workerClassName).asSubclass(Worker::class.java)
        val constructor = workerClass.getDeclaredConstructor(Context::class.java, WorkerParameters::class.java)
        val instance = constructor.newInstance(appContext, workerParameters)
        when (instance){
            is UpcomingNotification ->
                instance.tripRepository = tripRepository
            else -> null
        }
        return instance
    }
}