package com.example.cityguide.presentation.settings

import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.Toast
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

    var hour = 0
    var minutes = 0
    var isCheckedUpcomingNotification = false
    companion object{
        var isCheckedCommercialNotification = false
    }

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
                    val h = it.data?.hour
                    val min = it.data?.minutes
                    if (h != null) {
                        if (h > 12) {
                            requireView().timeExpect.text =
                                String.format("%02d", h - 12) + ":" + String.format(
                                    "%02d",
                                    min
                                ) + " PM"
                        } else {
                            requireView().timeExpect.text =
                                String.format("%02d", h) + ":" + String.format(
                                    "%02d",
                                    min
                                ) + " AM"
                        }
                    }


                    if (h != null && min != null) {
                        setTimeButton.text = "change time"
                        hour = h
                        minutes = min
                    }
                    requireView().switch1.setOnCheckedChangeListener(null)
                    requireView().switch2.setOnCheckedChangeListener(null)
                    requireView().switch1.isChecked = it.data?.isUpcomingActive == true
                    requireView().switch2.isChecked = it.data?.isCommercialActive == true
                    isCheckedUpcomingNotification = it.data?.isUpcomingActive == true
                    isCheckedCommercialNotification = it.data?.isCommercialActive == true
                    view?.switch2?.setOnCheckedChangeListener { buttonView, isChecked ->
                        isCheckedCommercialNotification = isChecked
                        settingsVM.insertTime(
                            hour,
                            minutes,
                            isCheckedUpcomingNotification,
                            isCheckedCommercialNotification
                        )
                    }
                    view?.switch1?.setOnCheckedChangeListener { buttonView, isChecked ->
                        isCheckedUpcomingNotification = isChecked
                        settingsVM.insertTime(hour, minutes, isCheckedUpcomingNotification, isCheckedCommercialNotification)
                        if(isChecked){
                            startNotification()
                        }
                    }
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

            setTimeButton.text = "change time"
            hour = h
            minutes = min

            settingsVM.insertTime(hour, minutes, isCheckedUpcomingNotification, isCheckedCommercialNotification)
            startNotification()
        }

    }

    fun startNotification(){

        if(isCheckedUpcomingNotification == true && hour != null && minutes != null) {

            val currentDate = Calendar.getInstance()
            val dueDate = Calendar.getInstance()

            dueDate.set(Calendar.HOUR_OF_DAY, hour)
            dueDate.set(Calendar.MINUTE, minutes)
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

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)

    }

}