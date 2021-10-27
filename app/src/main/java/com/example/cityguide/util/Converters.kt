package com.example.cityguide.util

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Converters {

    private val dateFormatter = DateTimeFormatter.ofPattern("u-M-d")

    @TypeConverter
    fun dateFromTimestamp(value: Long?) : LocalDate? = value?.let { LocalDate.ofEpochDay(it) }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?) : Long? = date?.toEpochDay()

    @TypeConverter
    fun dateFromString(value: String?) : LocalDate? = value?.let { LocalDate.parse(it, dateFormatter) }

    fun monthConverter(month: Int) = month + 1
}