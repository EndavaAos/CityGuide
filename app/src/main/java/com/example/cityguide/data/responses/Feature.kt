package com.example.cityguide.data.responses

data class Feature(
    val geometry: Geometry,
    val id: String,
    val properties: Properties,
    val type: String
)