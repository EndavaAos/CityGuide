package com.example.cityguide.presentation.settings

import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cityguide.R
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.view.*


class SettingsFragment : Fragment(R.layout.fragment_settings) {

    lateinit var amPm: String
    lateinit var minString: String
    lateinit var hString: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        view.setTimeButton.setOnClickListener {
            openTimePicker()
        }

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
        }

        requireView()

    }

}