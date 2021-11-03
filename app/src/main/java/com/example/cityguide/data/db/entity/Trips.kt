package com.example.cityguide.data.db.entity

import android.os.Parcelable
import androidx.room.*
import com.example.cityguide.util.Converters
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.time.LocalDate

@Entity
data class Trips (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val country: String,
    val listOfPOI: List<Trip>,
    var dateStart: LocalDate?,
    var dateEnd: LocalDate?
    ): Serializable
{
    constructor(id: Int, name: String, country: String, dateStart: LocalDate, dateEnd: LocalDate) :
            this(id, name, country, listOf(), dateStart, dateEnd)

    constructor(id: Int, name: String, country: String, dateStart: LocalDate, dateEnd: LocalDate, listOfPOI: List<Trip>) :
            this(id, name, country, listOfPOI, dateStart, dateEnd)

    data class Trip(
        val xid: String,
        @Embedded(prefix = "adress_")
        val address: TripAddress,
        @Embedded(prefix = "bbox_")
        val bbox: TripBBox,
        val kinds: String,
        val name: String,
        @Embedded(prefix = "preview_")
        val preview: Preview,
        @Embedded(prefix = "wiki_")
        val wikipedia_extracts: TripDetails,
    ): Serializable {

        data class TripAddress(
            val city: String,
            val house_number: String,
            val road: String,
            val postcode: String,
            val suburb: String,
            val county: String,
            val country: String,
            val country_code: String
        ): Serializable

        data class TripBBox(
            val lon_min: Double?,
            val lon_max: Double?,
            val lat_min: Double?,
            val lat_max: Double?
        ): Serializable

        data class TripDetails(
            val title: String,
            val text: String
        ): Serializable


        data class Preview(
            val source: String,
        ): Serializable
    }
}