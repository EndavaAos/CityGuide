package com.example.cityguide.presentation.makeATrip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.cityguide.R
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.alert_dialog_view.view.*
import kotlinx.android.synthetic.main.fragment_make_trip.*
import kotlinx.android.synthetic.main.fragment_make_trip.view.*
import java.text.SimpleDateFormat
import java.util.*

class MakeTripFragment : Fragment(R.layout.fragment_make_trip) {

    lateinit var button: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_make_trip, container, false)

        button = view.findViewById(R.id.calendarButton)

        view.calendarButton.setOnClickListener {
            onButtonCick()
            showDataRangePicker()
        }

        view.calendarButton2.setOnClickListener {
            if (expect.text.equals("-")) {
                val view = View.inflate(context, R.layout.alert_dialog_view, null)
                val builder = AlertDialog.Builder(requireContext())
                builder.setView(view)

                val dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(R.drawable.gradient_alert_dialog)
                dialog.setCancelable(false)

                view.yes.setOnClickListener {
                    dialog.dismiss()
                }

                view.setPeriod.setOnClickListener {
                    showDataRangePicker()
                    dialog.dismiss()
                }

                view.dismiss.setOnClickListener {
                    dialog.dismiss()
                }
            }

        }
        return view
    }

    private fun showDataRangePicker(): Boolean {

        val dateRangePicker =
            MaterialDatePicker
                .Builder.dateRangePicker()
                .setTitleText("Select Trip Period")
                .build()

        dateRangePicker.show(
            requireActivity().supportFragmentManager,
            "date_range_picker"
        )

        dateRangePicker.addOnPositiveButtonClickListener { dateSelected ->

            val startDate = dateSelected.first
            val endDate = dateSelected.second

            if (startDate != null && endDate != null) {
                calendarButton.text = "Edit Trip Period"

                requireView().expect.text =
                    "${convertLongToTime(startDate)} - ${convertLongToTime(endDate)}"
            }
        }
        return true
    }

    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat(
            "dd.MM.yyyy",
            Locale.getDefault()
        )
        return format.format(date)
    }

    private fun onButtonCick() {
        val buttonClick = AlphaAnimation(1F, 0.8F)
        button.startAnimation(buttonClick)
    }

}