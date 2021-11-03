package com.example.cityguide.presentation.trips.tripSegment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cityguide.data.db.entity.Trips
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class GeneralTripViewModel : ViewModel() {

    abstract val data: Observable<List<Trips>>

    val trips = MutableLiveData<List<Trips>>()

    private val compositeDisposable = CompositeDisposable()

    fun getTrips() {
        compositeDisposable.add(
            data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result -> trips.value = result }
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}