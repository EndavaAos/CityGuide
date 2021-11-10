package com.example.cityguide.presentation.settings

import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.work.Configuration
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.example.cityguide.R
import com.example.cityguide.data.responses.Resource
import com.example.cityguide.data.services.UpcomingNotification
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.view.*
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SettingsFragment : Fragment(R.layout.fragment_settings) {

    lateinit var amPm: String
    lateinit var minString: String
    lateinit var hString: String

    @Inject
    lateinit var settingsVM: SettingsVM


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        view.setTimeButton.setOnClickListener {
            openTimePicker()
        }

        settingsVM.getTime()

        settingsVM.timeData.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    requireView().timeExpect.text = "${it.data?.hour}:${it.data?.minutes}"
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {
                }
            }
        })

        return view
    }

    private fun openTimePicker() {
        val isSystem24Hour = is24HourFormat(requireContext())
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_12H else TimeFormat.CLOCK_24H

        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(12)
            .setMinute(0)
            .setTitleText("SELECT NOTIFICATION TIME")
            .build()
        picker.show(childFragmentManager, "TAG")

        val switch = view?.findViewById<SwitchCompat>(R.id.switch1)

        picker.addOnPositiveButtonClickListener {

            val h = picker.hour
            val min = picker.minute

            if (min < 10) {
                minString = "0$min"
            } else {
                minString = "$min"
            }

            if (h < 10) {
                hString = "0$h"
            } else {
                hString = "$h"
            }

            if (h != null && min != null) {
                setTimeButton.text = "change time"
            }
            requireView().timeExpect.text = "$hString:$minString"
            settingsVM.insertTime(h, min)

            val currentDate = Calendar.getInstance()
            val dueDate = Calendar.getInstance()

            dueDate.set(Calendar.HOUR_OF_DAY, h)
            dueDate.set(Calendar.MINUTE, min)
            dueDate.set(Calendar.SECOND, 0)
            if (dueDate.before(currentDate)) {
                dueDate.add(Calendar.HOUR_OF_DAY, 24)
            }
            val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis
            val dailyWorkRequest = OneTimeWorkRequestBuilder<UpcomingNotification>()
                .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
                .build()
            WorkManager.getInstance(requireContext()).cancelAllWork()
            WorkManager.getInstance(requireContext()).enqueue(dailyWorkRequest)
        }

        requireView()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)

    }

}