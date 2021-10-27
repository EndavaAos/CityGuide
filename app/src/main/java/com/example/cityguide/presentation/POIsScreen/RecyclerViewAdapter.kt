package com.example.cityguide.presentation.POIsScreen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cityguide.R
import com.example.cityguide.data.models.LocationPOIScreen
import kotlinx.android.synthetic.main.item_poi.view.*

class RecyclerViewAdapter(
    private val context: Context
) : RecyclerView.Adapter<RecyclerViewAdapter.POIViewHolder>() {

    lateinit var locations: MutableList<LocationPOIScreen>

    fun setLocationMutableList(list: MutableList<LocationPOIScreen>){
        locations = list
    }


    class POIViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

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

        holder.itemView.nameText.text = currentCity.name
        val value = currentCity.kinds.split(",")[0]
        holder.itemView.categoryText.text = value

        holder.itemView.checkBox.setOnClickListener {
            if(!currentCity.isChecked)
            {
                holder.itemView.checkBox.speed = 1f
            }
            else
            {
                holder.itemView.checkBox.speed = -1f;
            }
            currentCity.isChecked = !(currentCity.isChecked)
            holder.itemView.checkBox.playAnimation()
        }


    }

    override fun getItemCount(): Int {
        return locations.size
    }

}