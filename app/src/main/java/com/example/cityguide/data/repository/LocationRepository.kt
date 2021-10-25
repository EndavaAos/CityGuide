package com.example.cityguide.data.repository

import com.example.cityguide.data.responses.BitCoinRespone
import io.reactivex.rxjava3.core.Observable

interface  LocationRepository {

    fun getTrades() : Observable<BitCoinRespone>
}