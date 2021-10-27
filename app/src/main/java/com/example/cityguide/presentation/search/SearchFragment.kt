package com.example.cityguide.presentation.search

import android.app.Activity
import android.content.Context
import android.graphics.LightingColorFilter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
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
import com.example.cityguide.R

class SearchFragment: Fragment(R.layout.fragment_search) {

    lateinit var searchButton: Button
    lateinit var searchEditText: EditText


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchButton = view.findViewById(R.id.search_button_trip)
        searchEditText = view.findViewById(R.id.search_input_edittext)

        searchEditText.addTextChangedListener { buttonStatusOnTextChanged() }
        searchButton.setOnClickListener { onSearchButtonClick() }
    }


    private fun buttonStatusOnTextChanged() {
        searchButton.isEnabled = true
        searchButton.setBackgroundResource(R.drawable.gradient_button)
    }

    private fun onSearchButtonClick() {
        val buttonClick = AlphaAnimation(1F, 0.8F)
        searchButton.startAnimation(buttonClick)

        val inputText = searchEditText.text // Text from EditText
            // TODO ("Implement POI page")

        // Delete after implementation
        Toast.makeText(context, searchEditText.text, Toast.LENGTH_SHORT).show()

        searchEditText.clearFocus()
        searchEditText.text.clear()
    }
}