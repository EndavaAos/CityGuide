package com.example.cityguide.presentation.location

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cityguide.data.repository.LocationRepository
import com.example.cityguide.data.responses.BitCoinRespone
import com.example.cityguide.data.responses.Resource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class LocationSearchVM @Inject constructor(private val repository: LocationRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val tradesLiveData = MutableLiveData<Resource<BitCoinRespone>>()

    init {
        getTrades()
    }

    fun getTrades() {
        tradesLiveData.value = Resource.Loading()
        compositeDisposable.add(
            repository.getTrades().observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result -> tradesLiveData.value = Resource.Success(result) }, // onNext
                    { error -> tradesLiveData.value = Resource.Error(error.message ?: "") },
                    { Log.d("Test", "Complete") }) // onComplete
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}