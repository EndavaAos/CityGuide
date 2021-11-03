package com.example.cityguide.presentation.POIDetails

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
import androidx.core.view.marginTop
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import kotlinx.android.synthetic.main.fragment_poi_details.*
import kotlinx.android.synthetic.main.fragment_poi_details.view.*
import kotlin.math.abs



class PoiDetailsFragment : Fragment(R.layout.fragment_poi_details) {

    @Inject
    lateinit var vm: PoiDetailsVM

    @Inject
    lateinit var locationRepositoryImpl: LocationRepositoryImpl

    val args: PoiDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        behaviour.overlayTop = 60

        val drawable1: Drawable = resources.getDrawable(R.drawable.gradient_background)
        val drawable2: Drawable = resources.getDrawable(R.drawable.gradient_bg)


        // White CORNERS Bug
        appBar?.addOnOffsetChangedListener(object : OnOffsetChangedListener {


            private var state: State? = null
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {

                state = if (verticalOffset == 0) {
                    if (state !== State.EXPANDED) {
                        nested.background = drawable2
                    }
                    State.EXPANDED
                } else if (abs(verticalOffset) >= appBarLayout.totalScrollRange) {
                    if (state !== State.COLLAPSED) {
                        nested.background = drawable1
                    }
                    State.COLLAPSED
                } else {
                    if (state !== State.IDLE) {
                        nested.background = drawable2
                    }
                    State.IDLE
                }
            }
        })

        val xidFromFragment = args.xid

        var finalAddress = ""

        fun String.capitalizeWords(): String = split(" ").joinToString(" ") { it.capitalize() }

        addrLayout?.visibility = View.INVISIBLE
        descrLayout?.visibility = View.INVISIBLE

        vm.getPoiDetails(
            xidFromFragment,
            "5ae2e3f221c38a28845f05b6dd571f66600ae5630f709863edc61b5d"
        )

        vm.poiDetailsLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    name?.text = it.data?.name

                    if (it.data?.address?.house_number != null) {
                        finalAddress += it.data.address.house_number + " "
                    }
                    if (it.data?.address?.road != null) {
                        finalAddress += it.data.address.road
                    }
                    if (it.data?.address?.suburb != null) {
                        finalAddress += ", " + it.data.address.suburb
                    }
                    if (it.data?.address?.city != null) {
                        finalAddress += it.data.address.city
                    }
                    if (it.data?.address?.country != null) {
                        finalAddress += ", " + it.data.address.county
                    }
                    if (it.data?.address?.postcode != null) {
                        finalAddress += ", " + it.data.address.postcode
                    }
                    if (it.data?.address?.country != null) {
                        finalAddress += " " + it.data.address.country
                    }

                    address?.text = finalAddress
                    addrLayout?.visibility = View.VISIBLE


                    if (it.data?.wikipedia_extracts?.text == null) {
                        descriptionTxt?.visibility = View.INVISIBLE
                        descrLayout?.visibility = View.INVISIBLE
                    } else {
                        description?.text = it.data.wikipedia_extracts.text
                        descrLayout?.visibility = View.VISIBLE
                    }

                    val kindsString = it.data?.kinds
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
                        Glide.with(requireContext()).load(it.data.preview.source)
                            .placeholder(R.drawable.poi_placeholder).into(image)
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(context, "error: ${it.message}", Toast.LENGTH_LONG).show()
                }
            }
        })

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }
}