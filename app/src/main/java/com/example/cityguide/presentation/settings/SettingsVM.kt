package com.example.cityguide.presentation.settings

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cityguide.data.db.entity.NotificationConfig
import com.example.cityguide.data.repository.UpcomingNotificationTimeRepository
import com.example.cityguide.data.responses.Resource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class SettingsVM @Inject constructor(private val repository: UpcomingNotificationTimeRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val timeData = MutableLiveData<Resource<NotificationConfig>>()

    fun getTime(){
        timeData.value = Resource.Loading()
        compositeDisposable.add(
            repository.getUpcomingNotificationTime().observeOn(AndroidSchedulers.mainThread()).subscribe( { result -> timeData.value = Resource.Success(result)},
                { error -> timeData.value = Resource.Error(error.message ?: "")},
                {Log.d("Test", "Complete") })
        )
    }

    fun insertTime(hour: Int, minutes: Int, isCheckedUpcoming: Boolean, isCheckedCommercial: Boolean){
        compositeDisposable.add(
            repository.updateUpcomingNotificationTime(hour, minutes, isCheckedUpcoming, isCheckedCommercial).subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}