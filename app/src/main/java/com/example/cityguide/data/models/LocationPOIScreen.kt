package com.example.cityguide.data.models

import dagger.Provides

data class LocationPOIScreen (
    val name: String,
    val kinds: String,
    var isChecked: Boolean = false
        )