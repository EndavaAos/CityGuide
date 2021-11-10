package com.example.cityguide.presentation.POIsScreen

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.cityguide.R
import com.example.cityguide.data.db.entity.Trips
import kotlinx.android.synthetic.main.fragment_make_trip.*

class ConfirmationDialogFragment( val trips: Trips, val databaseAction: String ) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(resources.getDrawable(R.drawable.background_confirmation_dialog))
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)

        val inflater: LayoutInflater = (activity?.layoutInflater) as LayoutInflater
        val view: View = inflater.inflate(R.layout.confirmation_layout, null)

        builder.setView(view)

        val yesButton: Button = view.findViewById(R.id.yesButton)
        val dismissButon: Button = view.findViewById(R.id.dismissButton)

        yesButton.setOnClickListener {
            val totalPoints = 0
            val action = POIScreenFragmentDirections.navigateFromPOIScreenToMakeTripFragment(trips, databaseAction)
            findNavController().navigate(action)

            dismiss()
        }

        dismissButon.setOnClickListener {
            dismiss()
        }

        return builder.create()
    }
}