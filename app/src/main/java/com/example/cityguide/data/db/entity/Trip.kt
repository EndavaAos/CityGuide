package com.example.cityguide.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Trip(
    @PrimaryKey
    val xid: String,
    @Embedded
    val address: TripAddress,
    @Embedded
    val bbox: TripBBox,
    @Embedded
    val wikipedia_extracts: TripDetails,
    var dateStart: LocalDate? = null,
    var dateEnd: LocalDate? = null
) {

    data class TripAddress(
        val city: String,
        val country_code: String,
    )

    data class TripBBox(
        val lon_min: Int,
        val lon_max: Int,
        val lat_min: Int,
        val lat_max: Int
    )

    data class TripDetails(
        val title: String,
        val text: String
    )
}
