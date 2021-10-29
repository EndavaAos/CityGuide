package com.example.cityguide.data.repository

import android.util.Log
import com.example.cityguide.data.network.LocationApi
import com.example.cityguide.data.responses.BitCoinRespone
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    val locationApi: LocationApi
) : LocationRepository {

    override fun getTrades(): Observable<BitCoinRespone> {
        return locationApi.getTrades().subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map { result ->
                Log.d("Example", "Save in db")
                result
            } // Save in db first
            .onErrorResumeNext { Observable.just(BitCoinRespone()) } // in case of error, return from DB

    }
}