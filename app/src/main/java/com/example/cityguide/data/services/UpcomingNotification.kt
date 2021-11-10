package com.example.cityguide.data.services

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.MutableLiveData
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.cityguide.R
import com.example.cityguide.data.db.entity.Trips
import com.example.cityguide.data.repository.TripRepository
import com.example.cityguide.data.responses.Resource
import com.example.cityguide.di.AppComponent
import com.example.cityguide.di.DaggerAppComponent
import com.example.cityguide.presentation.POIsScreen.POIScreenActivity
import com.example.cityguide.presentation.SavedTrip.SeeTripActivity
import dagger.android.support.AndroidSupportInjection
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.*
import java.util.concurrent.TimeUnit


class UpcomingNotification(val context: Context, workerParameters: WorkerParameters): Worker(context, workerParameters) {

    lateinit var tripRepository: TripRepository

    private val compositeDisposable = CompositeDisposable()
    lateinit var trips: List<Trips>

    private var notificationId = 0
    private var requestCode = 0

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("WrongThread")
    override fun doWork(): Result {

        val year = Calendar.getInstance().get(Calendar.YEAR)
        val month = Calendar.getInstance().get(Calendar.MONTH) + 1
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

        println("$year $month $day")

        compositeDisposable.add(
            tripRepository.getAllTrips().observeOn(AndroidSchedulers.mainThread())
                .subscribe({result -> trips = result

                           trips.forEach {
                                val tripDay = it.dateStart?.dayOfMonth
                                val tripMonth = it.dateStart?.monthValue
                               if(day + 2 == tripDay && month == tripMonth)
                               {
                                   createNotification(it.name, it)
                               }
                           }},
                    { Log.d("Test", "Complete")})
        )


        val dailyWorkRequest = OneTimeWorkRequestBuilder<UpcomingNotification>()
            .setInitialDelay(24, TimeUnit.HOURS)
            .build()
        WorkManager.getInstance(applicationContext)
            .enqueue(dailyWorkRequest)
        println("UPCOMING NOTIFICATION")
        return Result.success()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotification(name: String, trip: Trips){

        val CHANNEL_ID = "Heads_Up_Notification"

        val intent = Intent(context, SeeTripActivity::class.java)
        intent.putExtra("trip", trip)
        val pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        requestCode += 1

        val builder = NotificationCompat.Builder(applicationContext, "1")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Notification")
            .setContentIntent(pendingIntent)
            .setContentText(name)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(Notification.DEFAULT_ALL)
            .setAutoCancel(true)

        builder.setChannelId(CHANNEL_ID)

        val notificationManager = NotificationManagerCompat.from(applicationContext)
        notificationManager.notify(notificationId,builder.build())
        notificationId += 1

    }


}