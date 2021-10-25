package com.example.cityguide.presentation.location

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cityguide.R
import com.example.cityguide.data.responses.Resource
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class LocationSearchFragment : Fragment(R.layout.fragment_location_search) {

    @Inject
    lateinit var vm: LocationSearchVM

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }
}