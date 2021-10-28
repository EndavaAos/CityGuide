package com.example.cityguide.presentation.trips

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cityguide.data.db.entity.Trip
import com.example.cityguide.data.repository.TripRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class TripViewModel @Inject constructor(
    private val repository: TripRepository
) : ViewModel() {

    val activeTrips = MutableLiveData<List<Trip>>()
    val upcomingTrips = MutableLiveData<List<Trip>>()
    val completedTrips = MutableLiveData<List<Trip>>()


    private val activeTripsCompositeDisposable = CompositeDisposable()
    private val upcomingTripsCompositeDisposable = CompositeDisposable()
    private val completedTripsCompositeDisposable = CompositeDisposable()

    init {
        getTrips()
    }

    private fun getTrips() {
        activeTripsCompositeDisposable.add(
            repository.getActiveTrips().observeOn(AndroidSchedulers.mainThread())
                .subscribe { result -> activeTrips.value = result }
        )
        upcomingTripsCompositeDisposable.add(
            repository.getActiveTrips().observeOn(AndroidSchedulers.mainThread())
                .subscribe { result -> activeTrips.value = result }
        )
        completedTripsCompositeDisposable.add(
            repository.getActiveTrips().observeOn(AndroidSchedulers.mainThread())
                .subscribe { result -> activeTrips.value = result }
        )
    }

    override fun onCleared() {
        super.onCleared()
        activeTripsCompositeDisposable.clear()
        upcomingTripsCompositeDisposable.clear()
        completedTripsCompositeDisposable.clear()
    }
}