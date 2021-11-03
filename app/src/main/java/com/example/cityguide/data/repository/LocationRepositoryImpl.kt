package com.example.cityguide.data.repository

import com.example.cityguide.data.db.entity.Trips
import com.example.cityguide.data.network.LocationApi
import com.example.cityguide.data.responses.LocationResponseItem
import com.example.cityguide.data.responses.SuggestionResponse
import com.example.cityguide.presentation.POIDetails.models.Poi
import io.reactivex.rxjava3.core.Observable
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

    override fun getPoiDetails(xid: String, apiKey: String): Observable<Poi> {
        return locationApi.getPoiDetails(xid, apiKey)
    }

    override fun getPoiDetailsForList(xid: String, apiKey: String): Observable<Trips.Trip> {
        return locationApi.getPoiDetailsForList(xid, apiKey)
    }


}