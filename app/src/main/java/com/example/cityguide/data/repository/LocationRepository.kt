package com.example.cityguide.data.repository

import com.example.cityguide.data.db.entity.Trips
import com.example.cityguide.data.responses.LocationResponseItem
import com.example.cityguide.data.responses.SuggestionResponse
import com.example.cityguide.presentation.POIDetails.models.Poi
import io.reactivex.rxjava3.core.Observable

interface  LocationRepository {

    fun getLocation(name: String, apiKey: String): Observable<LocationResponseItem>

    fun getSuggestion(name: String, radius: Int, longitude: Double, latitude: Double, apiKey: String): Observable<SuggestionResponse>

    fun getPoiDetails(xid: String, apiKey: String): Observable<Poi>

    fun getPoiDetailsForList(xid: String, apiKey: String): Observable<Trips.Trip>
}