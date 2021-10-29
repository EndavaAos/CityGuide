package com.example.cityguide.data.responses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Properties(
    val dist: Double,
    val highlighted_name: String,
    val kinds: String,
    val name: String,
    val osm: String,
    val rate: Int,
    val wikidata: String,
    val xid: String
): Parcelable