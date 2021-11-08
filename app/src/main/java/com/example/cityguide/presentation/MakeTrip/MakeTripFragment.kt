package com.example.cityguide.presentation.makeATrip

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cityguide.AlarmReceiver
import com.example.cityguide.R
import com.example.cityguide.presentation.MakeTrip.MakeTripVM
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.alert_dialog_view.view.*
import kotlinx.android.synthetic.main.fragment_make_trip.*
import kotlinx.android.synthetic.main.fragment_make_trip.view.*
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import javax.inject.Inject

class MakeTripFragment : Fragment(R.layout.fragment_make_trip) {

    lateinit var button: Button

    var MAX_CLICK_DURATION = 300

    @Inject
    lateinit var vm: MakeTripVM

    var startDateTrip: LocalDate? = null
    var endDateTrip: LocalDate? = null
    var startDateNotif: Long = 0

    lateinit var alarmManager: AlarmManager

    val args: MakeTripFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_make_trip, container, false)


        createNotificationChannel()


        button = view.findViewById(R.id.finishScheduleButton)

        val backButton = view.findViewById<ImageView>(R.id.backArrowButton)
        backButton.setOnClickListener {
            findNavController().navigate(R.id.navigateFromMakeTripFragmentToPOIScreenFragment)
        }

        val trips = args.trips


        val expPoints = trips.listOfPOI?.size
        view.expected_points.text = expPoints.toString()

        val intent: Intent? = activity?.intent
        val placeToSearch = intent?.getStringExtra("place")
        view.titleTrip.text = placeToSearch + " trip"



        view.setTripButton.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        setTripButton.background.setColorFilter(
                            Color.rgb(223, 223, 223),
                            PorterDuff.Mode.MULTIPLY
                        )
                        setTripButton.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.ic_calendar2,
                            0,
                            0,
                            0
                        )
                        setTripButton.setTextColor(Color.parseColor("#ced4d8"))
                    }
                    MotionEvent.ACTION_UP -> {
                        setTripButton.background.clearColorFilter()
                        setTripButton.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.ic_calendar,
                            0,
                            0,
                            0
                        )
                        setTripButton.setTextColor(Color.parseColor("#f0f1f3"))

                        val startClickTime = Calendar.getInstance().timeInMillis
                        val clickDuration: Long =
                            Calendar.getInstance().timeInMillis - startClickTime
                        if (clickDuration < MAX_CLICK_DURATION) {
                            showDataRangePicker()
                        }
                    }
                }
                return true
            }
        })


        view.finishScheduleButton.setOnClickListener {

            onButtonClick()
            if (expect.text.equals("-")) {
                val view = View.inflate(context, R.layout.alert_dialog_view, null)
                val builder = AlertDialog.Builder(requireContext())
                builder.setView(view)

                val dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(R.drawable.gradient_alert_dialog)
                dialog.setCancelable(false)

                view.yes.setOnClickListener {
                    vm.insertTrips(trips)
                    activity?.finish()
                    dialog.dismiss()
                }

                view.setPeriod.setOnClickListener {
                    showDataRangePicker()
                    dialog.dismiss()
                }

                view.dismiss.setOnClickListener {
                    dialog.dismiss()
                }
            } else {
                trips.dateStart = startDateTrip!!
                trips.dateEnd = endDateTrip!!
                vm.insertTrips(trips)

                Toast.makeText(context, "Reminder set!", Toast.LENGTH_LONG).show()

                val intent: Intent = Intent(context, AlarmReceiver::class.java)
                val pendingIntent: PendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

                alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val tenSecondsInMillis = 172800000
                alarmManager.set(AlarmManager.RTC_WAKEUP, startDateNotif - tenSecondsInMillis, pendingIntent)
                activity?.finish()

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

            startDateNotif = startDate

            startDateTrip =
                Instant.ofEpochMilli(startDate).atZone(ZoneId.systemDefault()).toLocalDate()
            endDateTrip = Instant.ofEpochMilli(endDate).atZone(ZoneId.systemDefault()).toLocalDate()

            if (startDate != null && endDate != null) {
                setTripButton.text = "Edit Trip Period"


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


    private fun onButtonClick() {
        val buttonClick = AlphaAnimation(1F, 0.8F)
        button.startAnimation(buttonClick)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)

    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val name: CharSequence = "Upcoming Trip"
            val description = "Channel for Alarm Manager"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("foxandroid", name, importance)
            channel.description = description

            val notificationManager = requireActivity().getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)

        }
    }


}