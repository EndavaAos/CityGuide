package com.example.cityguide.presentation.TripMap

import android.content.Context
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cityguide.data.db.entity.Trips
import com.example.cityguide.presentation.POIDetails.PoiDetailsFragment2Args
import com.example.cityguide.presentation.makeATrip.MakeTripFragmentArgs

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import android.graphics.drawable.BitmapDrawable
import androidx.core.content.res.ResourcesCompat
import android.graphics.drawable.Drawable

import androidx.annotation.DrawableRes
import android.graphics.*
import com.example.cityguide.R


class MapsFragment : Fragment() {

    val arguments: MapsFragmentArgs by navArgs()

    lateinit var mapTitle: TextView
    var cameraLat = 0.0
    var cameraLon = 0.0

    var tripBoxes: MutableMap<String, Trips.Trip.TripBBox> = mutableMapOf()

    private val callback = OnMapReadyCallback { googleMap ->

        for (location in tripBoxes.keys) {
            val averageLon =
                tripBoxes[location]!!.lon_min!!.plus(tripBoxes[location]!!.lon_max!!) / 2
            val averageLat =
                tripBoxes[location]!!.lat_min!!.plus(tripBoxes[location]!!.lat_max!!) / 2

            val marker = LatLng(averageLat, averageLon)
            googleMap.addMarker(MarkerOptions()
                .position(marker)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_maps_marker))
                .title(location))

            cameraLat += averageLat
            cameraLon += averageLon
        }

        val cameraPosition = LatLng(cameraLat / tripBoxes.size, cameraLon / tripBoxes.size)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraPosition, 14.5f))

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        for (tripPOI in arguments.trip.listOfPOI) {
            if (tripPOI.bbox != null) {
                tripBoxes[tripPOI.name] = tripPOI.bbox
            }
        }

        if (tripBoxes.isEmpty()) {
            findNavController().navigate(R.id.seeTripFragment)
            Toast.makeText(context, "There are no locations to show on map", Toast.LENGTH_SHORT).show()
        }
        else {
            mapTitle = view.findViewById(R.id.map_title)

            mapTitle.setBackgroundColor(Color.argb(80, 0, 0, 0))
            val tripTitle = "${arguments.trip.name} trip"
            mapTitle.text = tripTitle
        }
    }
}