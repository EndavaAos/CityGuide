package com.example.cityguide.data.network

import com.example.cityguide.data.responses.BitCoinRespone
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface LocationApi {

    @GET("/trades?since=5")
    fun getTrades() : Observable<BitCoinRespone>
}