package com.example.cityguide.data.services

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.MutableLiveData
import com.example.cityguide.R
import com.example.cityguide.data.db.entity.NotificationConfig
import com.example.cityguide.data.repository.UpcomingNotificationTimeRepository
import com.example.cityguide.data.responses.Resource
import com.example.cityguide.presentation.POIsScreen.POIScreenActivity
import com.example.cityguide.presentation.settings.SettingsFragment
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.android.AndroidInjection
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.view.*
import javax.inject.Inject

class PushNotificationService : FirebaseMessagingService() {


    @Inject
    lateinit var upcomingNotificationTimeRepository: UpcomingNotificationTimeRepository

    var timeData = NotificationConfig()

    override fun onCreate() {
        AndroidInjection.inject(this);
        super.onCreate()
    }


    @SuppressLint("RemoteViewLayout")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(p0: RemoteMessage) {
        val title = p0.data["title"]
        val city = p0.data["text"]

        upcomingNotificationTimeRepository.getUpcomingNotificationTime()
            .observeOn(AndroidSchedulers.mainThread()).subscribe({ result ->
            timeData = result
            if (timeData.isCommercialActive) {
                val collapsedView = RemoteViews(packageName, R.layout.collapsed_notification)
                collapsedView.setTextViewText(R.id.title_text, "Interesting Locations")
                collapsedView.setTextViewText(
                    R.id.description_text,
                    "Have you visited " + city + " already? If not, check it out on the app"
                )

                val CHANNEL_ID = "Heads_Up_Notification"
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    "CityGuide",
                    NotificationManager.IMPORTANCE_HIGH
                )

                getSystemService(NotificationManager::class.java).createNotificationChannel(
                    channel
                )

                println("WE'RE HERE")
                val intent = Intent(this, POIScreenActivity::class.java)
                intent.putExtra("place", city)
                intent.putExtra("database", "Insert")
                val pendingIntent =
                    PendingIntent.getActivity(
                        this,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )


                val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher_notification)
                    .setCustomContentView(collapsedView)
                    .setContentIntent(pendingIntent)
                    .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                    .setAutoCancel(true)

                val notificationManager = NotificationManagerCompat.from(this)
                notificationManager.notify(1, builder.build())
                super.onMessageReceived(p0)
            }
        },
            { Log.d("Test", "Complete") })
    }
}