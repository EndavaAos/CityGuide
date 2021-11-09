package com.example.cityguide.presentation.search

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.LightingColorFilter
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.cityguide.R
import com.example.cityguide.StartScreenActivity
import com.example.cityguide.presentation.POIsScreen.POIScreenActivity

class SearchFragment: Fragment(R.layout.fragment_search) {

    lateinit var searchButton: Button
    lateinit var searchEditText: EditText


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchButton = view.findViewById(R.id.search_button_trip)
        searchEditText = view.findViewById(R.id.search_input_edittext)

        searchEditText.addTextChangedListener { buttonStatusOnTextChanged() }
        searchButton.setOnClickListener { onSearchButtonClick() }
        searchButton.setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        searchButton.background.setColorFilter(Color.rgb(223, 223, 223), PorterDuff.Mode.MULTIPLY)
                    }
                    MotionEvent.ACTION_UP -> {
                        v.performClick()
                        searchButton.background.clearColorFilter()
                    }
                }
                return true
            }
        })
    }


    private fun buttonStatusOnTextChanged() {
        if (searchEditText.text.isNotEmpty()) {
            searchButton.isEnabled = true
            searchButton.setBackgroundResource(R.drawable.gradient_button)
        }
        else {
            searchButton.isEnabled = false
            searchButton.setBackgroundResource(R.drawable.rectangle_for_button_screen_search)
            searchEditText.clearFocus()
        }
    }

    private fun onSearchButtonClick() {
        val buttonClick = AlphaAnimation(1F, 0.8F)
        searchButton.startAnimation(buttonClick)

        val intent = Intent(context, POIScreenActivity::class.java)
        val inputText = searchEditText.text
        intent.putExtra("place", inputText.toString())
        intent.putExtra("database", "Insert")
        startActivity(intent)

        searchEditText.clearFocus()
        searchEditText.text.clear()
        searchButton.isEnabled = false
        searchButton.setBackgroundResource(R.drawable.rectangle_for_button_screen_search)
    }

    private fun onSearchButtonTouch() {

    }
}