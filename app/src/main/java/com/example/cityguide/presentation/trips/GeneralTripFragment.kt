package com.example.cityguide.presentation.trips

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.LiveData
import com.example.cityguide.R
import com.example.cityguide.data.db.entity.Trip
import com.example.cityguide.databinding.FragmentGeneralTripBinding

class GeneralTripFragment : Fragment(R.layout.fragment_general_trip) {

    private var _binding: FragmentGeneralTripBinding? = null
    private val binding get() = _binding!!

    private lateinit var title: String
    private lateinit var observableData: LiveData<List<Trip>>
    private lateinit var errorScreen: Fragment
    private lateinit var listScreen: TripsPreviewList

    private var isListExpanded = true

    fun setupFragment(
        title: String,
        observableData: LiveData<List<Trip>>,
        errorScreen: Fragment
    ) {
        this.title = title
        this.observableData = observableData
        this.errorScreen = errorScreen

        listScreen = TripsPreviewList()
        listScreen.setObservable(this.observableData)

        initializeListFragment()
    }

    private fun initializeListFragment() {
        parentFragmentManager.commit {
            add(R.id.trips_active, errorScreen)
            setReorderingAllowed(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeBinding(view)
    }

    private fun initializeBinding(view: View) {
        _binding = FragmentGeneralTripBinding.bind(view)

        binding.apply {
            tripsCategory.text = title

            observableData.observe(viewLifecycleOwner) {
                if (it.isEmpty()) {
                    setListFragment(errorScreen)
                } else {
                    setListFragment(listScreen)
                }
            }

            expandableButton.setOnClickListener {
                handleExpandCollapse()
            }
        }
    }

    private fun handleExpandCollapse() {
        // TODO:handle expanding/collapsing
    }

    private fun setListFragment(listFragment: Fragment) {
        parentFragmentManager.commit {
            replace(R.id.trips_active, listFragment)
            setReorderingAllowed(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}