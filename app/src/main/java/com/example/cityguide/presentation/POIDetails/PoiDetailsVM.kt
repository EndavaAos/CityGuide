package com.example.cityguide.presentation.POIDetails

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cityguide.data.repository.LocationRepository
import com.example.cityguide.data.responses.LocationResponseItem
import com.example.cityguide.data.responses.Resource
import com.example.cityguide.presentation.POIDetails.models.Poi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class PoiDetailsVM @Inject constructor(private val repository: LocationRepository): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val poiDetailsLiveData = MutableLiveData<Resource<Poi>>()

    fun getPoiDetails(xid: String, apiKey: String) {
        poiDetailsLiveData.value = Resource.Loading()
        compositeDisposable.add(
            repository.getPoiDetails(xid, apiKey).observeOn(AndroidSchedulers.mainThread())
                .subscribe({result -> poiDetailsLiveData.value = Resource.Success(result)},
                    { error -> poiDetailsLiveData.value = Resource.Error(error.message ?: "")},
                    { Log.d("Test", "Complete")})
        )
    }


}