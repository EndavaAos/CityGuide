package com.example.cityguide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cityguide.presentation.search.SearchFragment
import com.example.cityguide.presentation.trips.MyTripsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class StartScreenActivity : AppCompatActivity() {

    lateinit var navigationBar: BottomNavigationView

    private val fragmentManager = supportFragmentManager
    private val searchFragment: Fragment = SearchFragment()
    private val tripsFragment: Fragment = MyTripsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)

        navigationBar = findViewById(R.id.bottom_nav)

        navigationBar.setOnItemSelectedListener{
            navigationSelectionHandler(it)
        }
    }

    override fun onStart() {
        super.onStart()
        fragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, searchFragment)
            .commit()
    }

    private fun navigationSelectionHandler(menu: MenuItem): Boolean{
        when(menu.itemId) {
            R.id.nav_home -> {
                fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, searchFragment)
                    .commit()
                return true
            }
            R.id.nav_mytrip -> {
                fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, tripsFragment)
                    .commit()
                return true
            }
            R.id.nav_settings -> {
                //TODO ("Implement Settings page")

                // Delete after implementation
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> return false
        }
    }
}