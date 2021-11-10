package com.example.cityguide.presentation.SavedTrip

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.findFragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.cityguide.R
import com.example.cityguide.StartScreenActivity
import com.example.cityguide.presentation.POIDetails.PoiDetailsFragment
import com.example.cityguide.presentation.POIDetails.PoiDetailsFragment2
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_see_trip.*
import javax.inject.Inject

class SeeTripActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        requestWindowFeature(1)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        window.statusBarColor = resources.getColor(android.R.color.transparent)


        setContentView(R.layout.activity_see_trip)


    }

    override fun onResume() {
        super.onResume()

    }

    override fun onBackPressed() {

        val containerView: FragmentContainerView = findViewById(R.id.fragmentContainerView2)
        if (containerView.findNavController().currentDestination?.label == "PoiDetailsFragment2"){

            Navigation.findNavController(fragmentContainerView2).navigate(R.id.navigationFromPoiDetailsToSavedTrip)
        }else{
            this.finish()
        }

    }
}