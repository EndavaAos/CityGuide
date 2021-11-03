package com.example.cityguide.presentation.SavedTrip

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.example.cityguide.R
import com.example.cityguide.StartScreenActivity

class SeeTripActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_trip)

    }

    override fun onBackPressed() {
        val intent = Intent(this, StartScreenActivity::class.java)

        intent.putExtra("Check", "1")
        startActivity(intent)
    }
}