package com.example.cityguide.presentation.trips.tripSegment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityguide.data.db.entity.Trips
import com.example.cityguide.data.repository.TripRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.processors.PublishProcessor
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

abstract class GeneralTripViewModel(
    private val repository: TripRepository
) : ViewModel() {

    private val tripEventProcessor : PublishProcessor<TripEvent> = PublishProcessor.create()
    val tripEvent = tripEventProcessor as Flowable<TripEvent>

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

    fun onTripSelected(trips: Trips) = viewModelScope.launch {
        tripEventProcessor.onNext(TripEvent.NavigateToEditTripScreen(trips))
    }

    fun onTripSwiped(trips: Trips){
        compositeDisposable.add(
            repository.deleteTrip(trips).subscribeOn(Schedulers.io())
                .subscribe()
        )
        tripEventProcessor.onNext(TripEvent.ShowUndoDeleteTripMessage(trips))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun onUndoDeleteClick(trips: Trips){
        compositeDisposable.add(
            repository.insertTrips(trips).subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    sealed class TripEvent {
        data class NavigateToEditTripScreen(val trip: Trips) : TripEvent()
        data class ShowUndoDeleteTripMessage(val trip: Trips) : TripEvent()
    }
}