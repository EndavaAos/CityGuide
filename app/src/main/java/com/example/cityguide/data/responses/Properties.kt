package com.example.cityguide.data.responses

data class Properties(
    val dist: Double,
    val highlighted_name: String,
    val kinds: String,
    val name: String,
    val osm: String,
    val rate: Int,
    val wikidata: String,
    val xid: String
)