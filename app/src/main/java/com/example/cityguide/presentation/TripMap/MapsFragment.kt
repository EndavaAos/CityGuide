package com.example.cityguide.presentation.TripMap

import android.graphics.Color
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.example.cityguide.R
import com.example.cityguide.data.db.entity.Trips
import com.example.cityguide.presentation.POIDetails.PoiDetailsFragment2Args
import com.example.cityguide.presentation.makeATrip.MakeTripFragmentArgs

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    val arguments: MapsFragmentArgs by navArgs()

    lateinit var mapTitle: TextView
    lateinit var tripObject: Trips

    private val callback = OnMapReadyCallback { googleMap ->



        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        tripObject = arguments.trip

        mapTitle = view.findViewById(R.id.map_title)

        mapTitle.setBackgroundColor(Color.argb(80, 0, 0, 0))
        val tripTitle = "${tripObject.name} trip"
        mapTitle.text = tripTitle
    }
}