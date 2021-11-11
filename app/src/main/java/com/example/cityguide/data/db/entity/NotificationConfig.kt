package com.example.cityguide.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NotificationConfig(
    @PrimaryKey
    val id: Int = 0,
    val hour: Int = 0,
    val minutes: Int = 0,
    val isUpcomingActive: Boolean = true,
    val isCommercialActive: Boolean = true
)
