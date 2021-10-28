package com.example.cityguide.presentation.trips

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cityguide.data.db.entity.Trip
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class TripViewModel : ViewModel() {

    abstract val data: Observable<List<Trip>>

    val trips = MutableLiveData<List<Trip>>()

    private val compositeDisposable = CompositeDisposable()

    init {
        getTrips()
    }

    private fun getTrips() {
        compositeDisposable.add(
            data.observeOn(AndroidSchedulers.mainThread())
                .subscribe { result -> trips.value = result }
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}