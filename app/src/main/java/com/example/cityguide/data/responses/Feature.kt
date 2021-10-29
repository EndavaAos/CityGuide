package com.example.cityguide.data.responses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Feature(
    val geometry: Geometry,
    val id: String,
    val properties: Properties,
    val type: String
): Parcelable