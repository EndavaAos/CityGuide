package com.example.cityguide.presentation.trips

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.cityguide.R

class TripsErrorView(
    context: Context,
    attrs: AttributeSet
) : LinearLayout(context, attrs) {

    init {
        inflate(context, R.layout.trips_error_view, this)

        val image = findViewById<ImageView>(R.id.trips_error_view_image)
        val title = findViewById<TextView>(R.id.trips_error_view_title)
        val description = findViewById<TextView>(R.id.trips_error_view_description)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.TripsErrorView)
        image.setImageDrawable(attributes.getDrawable(R.styleable.TripsErrorView_trips_error_view_image))
        image.contentDescription = attributes.getString(R.styleable.TripsErrorView_trips_error_view_image_description)
        title.text = attributes.getString(R.styleable.TripsErrorView_trips_error_view_title)
        description.text = attributes.getString(R.styleable.TripsErrorView_trips_error_view_description)
        attributes.recycle()
    }
}