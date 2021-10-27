package com.example.cityguide.data.repository

import com.example.cityguide.data.responses.BitCoinRespone
import com.example.cityguide.data.responses.LocationResponseItem
import com.example.cityguide.data.responses.SuggestionResponse
import io.reactivex.rxjava3.core.Observable

interface  LocationRepository {

    fun getLocation(name: String, apiKey: String): Observable<LocationResponseItem>

    fun getSuggestion(name: String, radius: Int, longitude: Double, latitude: Double, apiKey: String): Observable<SuggestionResponse>
}