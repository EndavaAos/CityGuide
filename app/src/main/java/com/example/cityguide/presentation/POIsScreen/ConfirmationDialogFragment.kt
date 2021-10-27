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
import com.example.cityguide.R

class ConfirmationDialogFragment : DialogFragment() {

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
            dismiss()
        }

        dismissButon.setOnClickListener {
            dismiss()
        }


        return builder.create()
    }
}