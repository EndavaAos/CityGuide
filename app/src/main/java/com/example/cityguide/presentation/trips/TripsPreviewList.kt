package com.example.cityguide.presentation.trips

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cityguide.R
import com.example.cityguide.data.db.entity.Trip
import com.example.cityguide.databinding.TripsFragmentGeneralTripsListBinding

class TripsPreviewList : Fragment(R.layout.trips_fragment_general_trips_list) {

    private var _binding: TripsFragmentGeneralTripsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var observableData: LiveData<List<Trip>>

    fun setObservable(observable: LiveData<List<Trip>>) {
        observableData = observable
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeBinding(view)
    }

    private fun initializeBinding(view: View) {
        _binding = TripsFragmentGeneralTripsListBinding.bind(view)

        val tripAdapter = TripPreviewAdapter()

        binding.apply {
            recyclerView.apply {
                adapter = tripAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            observableData.observe(viewLifecycleOwner) {
                tripAdapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}