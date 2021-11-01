package com.example.cityguide.data.models

import android.os.Parcelable
import com.example.cityguide.data.responses.Feature
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate

@Parcelize
data class ListOfPOI (

    var listOfPoints: MutableList<Feature>,
    var dateStart: LocalDate?,
    var dateEnd: LocalDate?

): Parcelable