package com.example.cityguide.presentation.trips.tripSegment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityguide.data.db.entity.Trips
import com.example.cityguide.data.repository.TripRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class GeneralTripViewModel(
    private val repository: TripRepository
) : ViewModel() {

    private val tripEventChannel = Channel<TripEvent>()
    val tripEvent = tripEventChannel.receiveAsFlow()

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

    fun onTripSelected(trip: Trips) = viewModelScope.launch {
        tripEventChannel.send(TripEvent.NavigateToEditTripScreen(trip))
    }

    fun onTripSwiped(trip: Trips) = viewModelScope.launch {
        repository.deleteTrip(trip)
//        tripEventChannel.send(TripEvent.ShowUndoDeleteTripMessage(trip))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun onUndoDeleteClick(trip: Trips) = viewModelScope.launch {
        repository.insertTrips(trip)
    }

    sealed class TripEvent {
        data class NavigateToEditTripScreen(val trip: Trips) : TripEvent()
        data class ShowUndoDeleteTripMessage(val trip: Trips) : TripEvent()
    }
}