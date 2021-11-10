package com.example.cityguide.data.services

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.cityguide.R
import com.example.cityguide.presentation.POIsScreen.POIScreenActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotificationService: FirebaseMessagingService() {

    @SuppressLint("RemoteViewLayout")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(p0: RemoteMessage) {
        val title = p0.data["title"]
        val city = p0.data["text"]

        val collapsedView = RemoteViews(packageName, R.layout.collapsed_notification)
        collapsedView.setTextViewText(R.id.title_text, "Interesting Locations")
        collapsedView.setTextViewText(R.id.description_text, "Have you visited " + city + " already? If not, check it out on the app")

        val CHANNEL_ID = "Heads_Up_Notification"
        val channel = NotificationChannel(
            CHANNEL_ID,
            "CityGuide",
            NotificationManager.IMPORTANCE_HIGH
        )

        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)

        println("WE'RE HERE")
        val intent = Intent(this, POIScreenActivity::class.java)
        intent.putExtra("place", city)
        intent.putExtra("database", "Insert")
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_notification)
            .setCustomContentView(collapsedView)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(1,builder.build())
        super.onMessageReceived(p0)
    }
}