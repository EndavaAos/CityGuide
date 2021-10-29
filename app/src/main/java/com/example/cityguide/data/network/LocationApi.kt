package com.example.cityguide.data.network

import com.example.cityguide.data.responses.BitCoinRespone
import com.example.cityguide.data.responses.LocationResponseItem
import com.example.cityguide.data.responses.SuggestionResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationApi {

    @GET("geoname")
    fun getLocation(@Query("name") name: String, @Query("apikey") apikey: String): Observable<LocationResponseItem>

    @GET("autosuggest")
    fun getSuggestion(@Query("name") name: String, @Query("radius") radius: Int, @Query("lon") longitude: Double, @Query("lat") latitude: Double, @Query("apikey") apiKey: String): Observable<SuggestionResponse>
}