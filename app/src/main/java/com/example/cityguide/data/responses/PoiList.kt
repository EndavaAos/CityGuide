package com.example.cityguide.data.responses

import com.example.cityguide.data.db.entity.Trips

data class PoiList(
    var listOfTrip: MutableList<Trips.Trip>
        )