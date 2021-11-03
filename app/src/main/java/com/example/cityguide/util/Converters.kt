package com.example.cityguide.util

import androidx.room.TypeConverter
import com.example.cityguide.data.db.entity.Trips
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


object Converters {

    private val dateFormatter = DateTimeFormatter.ofPattern("d.M.u")

    @TypeConverter
    fun dateFromTimestamp(value: Long?) : LocalDate? = value?.let { LocalDate.ofEpochDay(it) }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?) : Long? = date?.toEpochDay()

    fun dateFromString(value: String?) : LocalDate? = value?.let { LocalDate.parse(it, dateFormatter) }

    fun dateToString(value: LocalDate?) : String? = value?.format(dateFormatter)

    fun monthConverter(month: Int) = month + 1

    @TypeConverter
    fun fromTripsList(value: List<Trips.Trip>?): String? {
        val gson = Gson()
        val type = object : TypeToken<List<Trips.Trip>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toTripsList(value: String?): List<Trips.Trip>? {
        val gson = Gson()
        val type = object : TypeToken<List<Trips.Trip>>() {}.type
        return gson.fromJson(value, type)
    }
}