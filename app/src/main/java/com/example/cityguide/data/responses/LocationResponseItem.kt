package com.example.cityguide.data.responses

data class LocationResponseItem (
    val name: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val population: Int
)