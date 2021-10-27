package com.example.cityguide.presentation.POIsScreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cityguide.data.repository.LocationRepository
import com.example.cityguide.data.responses.BitCoinRespone
import com.example.cityguide.data.responses.LocationResponseItem
import com.example.cityguide.data.responses.Resource
import com.example.cityguide.data.responses.SuggestionResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class LocationSearchVM @Inject constructor(private val repository: LocationRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()


    val locationLivedata = MutableLiveData<Resource<LocationResponseItem>>()
    val suggestionLiveData = MutableLiveData<Resource<SuggestionResponse>>()



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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}