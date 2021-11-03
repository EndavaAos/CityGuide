package com.example.cityguide.presentation.POIDetails

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.cityguide.R
import com.example.cityguide.data.repository.LocationRepositoryImpl
import com.example.cityguide.data.responses.Resource
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isInvisible
import androidx.navigation.NavArgs
import com.example.cityguide.data.db.entity.Trips
import com.google.android.material.appbar.AppBarLayout


class PoiDetailsFragment2 : Fragment(R.layout.fragment_poi_details) {

    @Inject
    lateinit var vm: PoiDetailsVM

    @Inject
    lateinit var locationRepositoryImpl: LocationRepositoryImpl

    val args: PoiDetailsFragment2Args by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        val name = view?.findViewById<TextView>(R.id.poi_name)
        val address = view?.findViewById<TextView>(R.id.poi_address)
        val description = view?.findViewById<TextView>(R.id.poi_description)
        val image = view?.findViewById<ImageView>(R.id.poi_image)
        val chipGrp = view?.findViewById<ChipGroup>(R.id.chipGroup)
        val nested = view?.findViewById<NestedScrollView>(R.id.nestedScrollView)
        val descrLayout = view?.findViewById<LinearLayout>(R.id.descriptionLinearLayout)
        val descriptionTxt = view?.findViewById<TextView>(R.id.descriptionTxt)
        val appBar = view?.findViewById<AppBarLayout>(R.id.appBarLayout)
        val constraint = view?.findViewById<ConstraintLayout>(R.id.constraint)
        val addrLayout = view?.findViewById<LinearLayout>(R.id.addressLinearLayout)

        val params = nested?.layoutParams as CoordinatorLayout.LayoutParams
        val behaviour = params.behavior as AppBarLayout.ScrollingViewBehavior
        behaviour.overlayTop = 100

        val trip: Trips.Trip = args.trip

        var finalAddress = ""

        fun String.capitalizeWords(): String = split(" ").joinToString(" ") { it.capitalize() }

        name?.text = trip.name

        addrLayout?.visibility = View.INVISIBLE
        descrLayout?.visibility = View.INVISIBLE

        if (trip.address?.house_number != null) {
            finalAddress += trip.address.house_number + " "
        }
        if (trip.address?.road != null) {
            finalAddress += trip.address.road
        }
        if (trip.address?.suburb != null) {
            finalAddress += ", " + trip.address.suburb
        }
        if (trip.address?.city != null) {
            finalAddress += trip.address.city
        }
        if (trip.address?.country != null) {
            finalAddress += ", " + trip.address.county
        }
        if (trip.address?.postcode != null) {
            finalAddress += ", " + trip.address.postcode
        }
        if (trip.address?.country != null) {
            finalAddress += " " + trip.address.country
        }

        address?.text = finalAddress
        addrLayout?.visibility = View.VISIBLE

        if (trip.wikipedia_extracts?.text == null) {
            descriptionTxt?.visibility = View.INVISIBLE
            descrLayout?.visibility = View.INVISIBLE
        } else {
            description?.text = trip.wikipedia_extracts.text
            descrLayout?.visibility = View.VISIBLE
        }


        val kindsString = trip.kinds
        val kindsArray = kindsString?.split(",")

        for (kind: String in kindsArray!!) {

            val chip = Chip(context)

            chip.chipBackgroundColor = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.background_start_color_30_darker_midnight
                )
            )
            chip.chipStrokeColor = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.primary_text_color
                )
            )


            chip.chipStrokeWidth = 2F
            chip.setTextColor(resources.getColor(R.color.primary_text_color))
            chip.text = kind.replace("_", " ").capitalizeWords()
            chip.textSize = 12F
            chip.textAlignment = View.TEXT_ALIGNMENT_CENTER
            chipGrp?.addView(chip)
        }


        if (image != null) {
            Glide.with(requireContext()).load(trip.preview?.source)
                .placeholder(R.drawable.poi_placeholder).into(image)
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }
}