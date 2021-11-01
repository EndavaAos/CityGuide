package com.example.cityguide.presentation.POIsScreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cityguide.data.db.entity.Trips
import com.example.cityguide.data.repository.LocationRepository
import com.example.cityguide.data.responses.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import kotlin.collections.ArrayList

class LocationSearchVM @Inject constructor(private val repository: LocationRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()


    val locationLivedata = MutableLiveData<Resource<LocationResponseItem>>()
    val suggestionLiveData = MutableLiveData<Resource<SuggestionResponse>>()
    var poiDetailsLiveData = MutableLiveData<Resource<Trips.Trip>>()
    var poiDetalisLiveDataList = MutableLiveData<Resource<PoiList>>()


    fun getLocation(name: String, apiKey: String){
        locationLivedata.value = Resource.Loading()
        compositeDisposable.add(
            repository.getLocation(name, apiKey).observeOn(AndroidSchedulers.mainThread())
                .subscribe({result -> locationLivedata.value = Resource.Success(result)
                           getSuggestion(result.name, 1000, result.lon, result.lat, "5ae2e3f221c38a28845f05b6dd571f66600ae5630f709863edc61b5d")},
            { error -> locationLivedata.value = Resource.Error(error.message ?: "")},
                    {Log.d("Test", "Complete") })
        )
    }

    fun getSuggestion(name: String, radius: Int, longitude: Double, latitude: Double, apiKey: String){
        suggestionLiveData.value = Resource.Loading()
        compositeDisposable.add(
            repository.getSuggestion(name, radius, longitude, latitude, apiKey).observeOn(AndroidSchedulers.mainThread())
                .subscribe({result -> suggestionLiveData.value = Resource.Success(result) },
                    { error -> locationLivedata.value = Resource.Error(error.message ?: "")},
                    {Log.d("Test", "Complete")})
        )
    }


    fun getPoiDetailsForList(xid: MutableList<String>, apiKey: String) {
        poiDetailsLiveData.value = Resource.Loading()

        val requests = ArrayList<Observable<Trips.Trip>>()
        xid.forEach {
            requests.add(repository.getPoiDetailsForList(it, apiKey))
        }

        val listOf = PoiList(arrayListOf<Trips.Trip>())

        compositeDisposable.add(
            Observable.merge(requests)
                .take(requests.size.toLong())
                .doFinally {
                    val listOfTrips2 = Resource.Success(listOf)
                    poiDetalisLiveDataList.postValue(listOfTrips2)
                }
                .subscribeOn(Schedulers.io())
                .subscribe { result -> listOf.listOfTrip.add(result) }
        )
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}