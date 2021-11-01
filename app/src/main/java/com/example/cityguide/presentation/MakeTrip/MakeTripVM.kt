package com.example.cityguide.presentation.MakeTrip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityguide.data.db.entity.Trips
import com.example.cityguide.data.repository.TripRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MakeTripVM@Inject constructor(private val repository: TripRepository) : ViewModel() {

    fun insertTrips(trips: Trips){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTrips(trips)
        }
    }

}