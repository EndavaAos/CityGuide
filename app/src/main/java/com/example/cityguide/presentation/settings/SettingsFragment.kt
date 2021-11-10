package com.example.cityguide.presentation.settings

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cityguide.AlarmReceiver
import com.example.cityguide.R
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.view.*
import java.util.*


class SettingsFragment : Fragment(R.layout.fragment_settings) {

    lateinit var calendar: Calendar
    lateinit var alarmManager: AlarmManager
    lateinit var pendingIntent: PendingIntent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        createNotificationChannel()
        view.setTimeButton.setOnClickListener {
            openTimePicker()
        }

        return view
    }

    private fun openTimePicker() {

        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("SELECT NOTIFICATION TIME")
            .build()
        picker.show(childFragmentManager, "TAG")

        picker.addOnPositiveButtonClickListener {

            val h = picker.hour
            val min = picker.minute

            if (h > 12) {
                requireView().timeExpect.text =
                    String.format("%02d", picker.hour - 12) + ":" + String.format(
                        "%02d",
                        picker.minute
                    ) + " PM"
            } else {
                requireView().timeExpect.text =
                    String.format("%02d", picker.hour) + ":" + String.format(
                        "%02d",
                        picker.minute
                    ) + " AM"
            }

            calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = picker.hour
            calendar[Calendar.MINUTE] = picker.minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0


            if (h != null && min != null) {
                setTimeButton.text = "change time"
                setAlarm()
            }
        }

        requireView()

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "DOESNTMATTER"
            val description = "LAST TIME AT ENDAVA"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("foxandroid", name, importance)
            channel.description = description
            val notificationManager =
                requireActivity().getSystemService(NotificationManager::class.java)

            notificationManager.createNotificationChannel(channel)

        }
    }

    private fun setAlarm() {
        alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

}