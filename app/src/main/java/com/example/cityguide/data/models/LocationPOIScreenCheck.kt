package com.example.cityguide.data.models

import dagger.Provides

data class LocationPOIScreenCheck (
    val name: String,
    val kinds: String,
    var isChecked: Boolean = false,
    val xid: String
        )