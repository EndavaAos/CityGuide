package com.example.cityguide.presentation.MakeTrip

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityguide.data.db.entity.Trips
import com.example.cityguide.data.repository.TripRepository
import com.example.cityguide.data.responses.Resource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MakeTripVM@Inject constructor(private val repository: TripRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun insertTrips(trips: Trips){
        compositeDisposable.add(
            repository.insertTrips(trips).subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    fun updateTrips(trips: Trips){
        compositeDisposable.add(
            repository.updateTrips(trips).subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}