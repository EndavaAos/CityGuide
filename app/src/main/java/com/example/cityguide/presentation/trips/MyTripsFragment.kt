package com.example.cityguide.presentation.trips

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.example.cityguide.R
import com.example.cityguide.databinding.FragmentMyTripsBinding
import dagger.android.support.AndroidSupportInjection

class MyTripsFragment : Fragment(R.layout.fragment_my_trips) {

    private val viewModel: TripViewModel by viewModels()

    private var _binding: FragmentMyTripsBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeBinding(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeTripListsPreviews()
    }

    private fun initializeTripListsPreviews() {
        parentFragmentManager.commit {
            val activeTripsErrorFragment = Fragment(R.layout.no_active_trips)
            val upcomingTripsErrorFragment = Fragment(R.layout.no_upcoming_trips)
            val completedTripsErrorFragment = Fragment(R.layout.no_completed_trips)

            val activeTripsFragment = GeneralTripFragment()
            activeTripsFragment.setupFragment(
                "Active trips",
                viewModel.activeTrips,
                activeTripsErrorFragment
            )

            val upcomingTripsFragment = GeneralTripFragment()
            activeTripsFragment.setupFragment(
                "Upcoming trips",
                viewModel.upcomingTrips,
                upcomingTripsErrorFragment
            )

            val completedTripsFragment = GeneralTripFragment()
            activeTripsFragment.setupFragment(
                "Completed trips",
                viewModel.completedTrips,
                completedTripsErrorFragment
            )

            add(R.id.trips_active, activeTripsFragment)
            add(R.id.trips_upcoming, upcomingTripsFragment)
            add(R.id.trips_completed, completedTripsFragment)

            setReorderingAllowed(true)
        }
    }

    private fun initializeBinding(view: View) {
        _binding = FragmentMyTripsBinding.bind(view)

        binding.apply {
            tripsActive
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
