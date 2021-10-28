package com.example.cityguide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.cityguide.presentation.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class StartScreenActivity : AppCompatActivity() {


    lateinit var navigationBar: BottomNavigationView

    var fragmentManager = supportFragmentManager
    var searchFragment: Fragment = SearchFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)

        navigationBar = findViewById(R.id.bottom_nav)

        navigationBar.setOnItemSelectedListener{it -> navigationSelectionHandler(it)}
    }

    override fun onStart() {
        super.onStart()
        fragmentManager.beginTransaction()
            .replace(R.id.main_fragment_holder, searchFragment)
            .commit()
    }

    private fun navigationSelectionHandler(menu: MenuItem): Boolean{
        when(menu.itemId) {
            R.id.nav_home -> {
                fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_holder, searchFragment)
                    .commit()
                return true
            }
            R.id.nav_mytrip -> {
                //TODO ("Implement My Trips page")

                // Delete after implementation
                Toast.makeText(this, "My Trip", Toast.LENGTH_SHORT).show()
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