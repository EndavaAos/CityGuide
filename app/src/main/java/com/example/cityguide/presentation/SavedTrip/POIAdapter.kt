package com.example.cityguide.presentation.SavedTrip

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.cityguide.R
import com.example.cityguide.data.db.entity.Trips
import com.example.cityguide.data.models.LocationPOIScreen
import com.example.cityguide.data.models.LocationPOIScreenCheck
import com.example.cityguide.presentation.POIsScreen.POIScreenFragmentDirections
import kotlinx.android.synthetic.main.item_poi.view.*
import kotlinx.android.synthetic.main.item_poi.view.cardView
import kotlinx.android.synthetic.main.item_poi.view.categoryText
import kotlinx.android.synthetic.main.item_poi.view.nameText
import kotlinx.android.synthetic.main.item_poi2.view.*

class POIAdapter(
    val locations: MutableList<Trips.Trip>,
    private val context: Context
) : RecyclerView.Adapter<POIAdapter.POIViewHolder>() {


    class POIViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): POIViewHolder {
        return POIViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_poi2,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: POIViewHolder, position: Int) {
        val currentCity = locations[position]

        holder.itemView.nameText.text = currentCity.name
        val category = currentCity.kinds.split(",")[0].replace("_", " ").capitalize()
        holder.itemView.categoryText.text = category


        holder.itemView.cardView.setOnClickListener {
            //Toast.makeText(context, currentCity.xid.toString(), Toast.LENGTH_LONG).show()

            val action =
                SeeTripFragmentDirections.navigationFromSavedTripToPOIDetails(
                    currentCity
                )
            holder.itemView.findNavController().navigate(action)

        }

    }

    override fun getItemCount(): Int {
        return locations.size
    }

}