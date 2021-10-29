package com.example.cityguide.data.responses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Geometry(
    val coordinates: List<Double>,
    val type: String
): Parcelable