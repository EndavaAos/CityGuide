package com.example.cityguide.presentation.POIDetails.models

data class Poi(
    val address: Address,
    val image: String,
    val kinds: String,
    val name: String,
    val otm: String,
    val point: Point,
    val preview: Preview,
    val rate: String,
    val sources: Sources,
    val wikidata: String,
    val wikipedia: String,
    val wikipedia_extracts: WikipediaExtracts,
    val xid: String
)