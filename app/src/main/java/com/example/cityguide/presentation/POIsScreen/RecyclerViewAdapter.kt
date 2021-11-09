package com.example.cityguide.presentation.POIsScreen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.cityguide.R
import com.example.cityguide.data.models.LocationPOIScreenCheck
import kotlinx.android.synthetic.main.item_poi.view.*

class RecyclerViewAdapter(
    val locations: MutableList<LocationPOIScreenCheck>,
    private val context: Context,
    var index: Int
) : RecyclerView.Adapter<RecyclerViewAdapter.POIViewHolder>() {


    class POIViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var lastAction = false
    private var firstOpen  = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): POIViewHolder {
        return POIViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_poi,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: POIViewHolder, position: Int) {
        val currentCity = locations[position]

        if(holder.itemView.checkBox.speed == 1f && position == index - 1){
            if(!lastAction) {
                holder.itemView.checkBox.speed = 100f
            }
            holder.itemView.checkBox.playAnimation()
        }

        if(position == index && !lastAction){
            if(firstOpen) {
                holder.itemView.checkBox.speed = -1f
                holder.itemView.checkBox.playAnimation()
            }
            else
            {
                firstOpen = true
            }
        }


        if(currentCity.isChecked && position != index - 1){
            holder.itemView.checkBox.speed = 100f;
            holder.itemView.checkBox.playAnimation()
        }

        holder.itemView.nameText.text = currentCity.name
        val value = currentCity.kinds.split(",")[0]
        holder.itemView.categoryText.text = value

        holder.itemView.checkBox.setOnClickListener {
            if (!currentCity.isChecked) {
                locations.remove(currentCity)
                locations.add(index, currentCity)
                holder.itemView.checkBox.speed = 1f
                lastAction = true
                index++
            } else {
                locations.remove(currentCity)
                locations.add(index-1, currentCity)
                holder.itemView.checkBox.speed = -1f;
                lastAction = false
                index--
            }
            currentCity.isChecked = !(currentCity.isChecked)
            notifyDataSetChanged()
        }

        holder.itemView.cardView.setOnClickListener {

            val action =
                POIScreenFragmentDirections.navigateFromPoiScreenFragmentToPoiDetailsFragment(
                    currentCity.xid
                )
            holder.itemView.findNavController().navigate(action)

        }

    }

    override fun getItemCount(): Int {
        return locations.size
    }

}