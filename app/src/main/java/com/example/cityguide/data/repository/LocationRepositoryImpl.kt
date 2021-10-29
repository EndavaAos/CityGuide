package com.example.cityguide.data.repository

import android.util.Log
import com.example.cityguide.data.network.LocationApi
import com.example.cityguide.data.responses.BitCoinRespone
import com.example.cityguide.data.responses.LocationResponseItem
import com.example.cityguide.data.responses.SuggestionResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    val locationApi: LocationApi
) : LocationRepository {
    override fun getLocation(name: String, apiKey: String): Observable<LocationResponseItem> {
        return locationApi.getLocation(name, apiKey)
    }

    override fun getSuggestion(
        name: String,
        radius: Int,
        longitude: Double,
        latitude: Double,
        apiKey: String
    ): Observable<SuggestionResponse> {
        return locationApi.getSuggestion(name, radius, longitude, latitude, apiKey)
    }
}