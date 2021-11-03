package com.example.cityguide.presentation.trips

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cityguide.R
import com.example.cityguide.data.db.entity.Trips
import com.example.cityguide.databinding.TripsFragmentGeneralTripsListItemBinding
import com.example.cityguide.presentation.SavedTrip.SeeTripActivity
import com.example.cityguide.util.Converters
import javax.inject.Inject

class TripPreviewAdapter(private val listener: OnItemClickListener, val context: Context)
    : ListAdapter<Trips, TripPreviewAdapter.TripPreviewViewHolder>(TripPreviewComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripPreviewViewHolder {
        val binding =
            TripsFragmentGeneralTripsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TripPreviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TripPreviewViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)

            holder.itemView.setOnClickListener {
                val intent = Intent(context, SeeTripActivity::class.java)
                intent.putExtra("trip", currentItem)
                context.startActivity(intent)
            }
        }
    }

    inner class TripPreviewViewHolder(private val binding: TripsFragmentGeneralTripsListItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

            init {
                binding.apply {
                    root.setOnClickListener {
                        val position = adapterPosition
                        if (position != RecyclerView.NO_POSITION)
                            listener.onItemClick(getItem(position))
                    }
                }
            }

            fun bind(trips: Trips) {
                binding.apply {
                    tripItemTitle.text = itemView.context.getString(
                        R.string.trip_item_title_format,
                        trips.name,
                        trips.country
                    )

                    trips.dateStart?.also {
                        tripPeriod.text = itemView.context.getString(
                            R.string.trip_item_period_format,
                            Converters.dateToString(it),
                            Converters.dateToString(trips.dateEnd)
                        )
                    } ?: run {
                        tripPeriod.text = ""
                    }
                }
            }
        }

    interface OnItemClickListener {
        fun onItemClick(trip: Trips)
    }

    class TripPreviewComparator : DiffUtil.ItemCallback<Trips>() {
        override fun areItemsTheSame(oldItem: Trips, newItem: Trips): Boolean
            = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Trips, newItem: Trips): Boolean
            = oldItem == newItem
    }
}