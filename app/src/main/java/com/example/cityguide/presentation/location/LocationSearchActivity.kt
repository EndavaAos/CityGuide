package com.example.cityguide.presentation.location

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cityguide.R
import com.example.cityguide.data.repository.LocationRepositoryImpl
import com.example.cityguide.data.responses.Resource
import dagger.android.AndroidInjection
import javax.inject.Inject

class LocationSearchActivity : AppCompatActivity() {

    @Inject
    lateinit var vm: LocationSearchVM

    @Inject
    lateinit var locationRepositoryImpl: LocationRepositoryImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.location_search_activity)

        vm.tradesLiveData.observe(this, {
            when (it) {
                is Resource.Success -> {
                    Toast.makeText(this, "success: ${it.data}", Toast.LENGTH_LONG).show()
                }
                is Resource.Error -> {
                    Toast.makeText(this, "error: ${it.message}", Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                    Toast.makeText(this, "loading", Toast.LENGTH_LONG).show()

                }
            }
        })
    }
}