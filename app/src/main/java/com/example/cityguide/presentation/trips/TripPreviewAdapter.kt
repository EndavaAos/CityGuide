package com.example.cityguide.presentation.trips

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cityguide.R
import com.example.cityguide.data.db.entity.Trips
import com.example.cityguide.databinding.TripsFragmentGeneralTripsListItemBinding
import com.example.cityguide.util.Converters

class TripPreviewAdapter
    : ListAdapter<Trips, TripPreviewAdapter.TripPreviewViewHolder>(TripPreviewComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripPreviewViewHolder {
        val binding =
            TripsFragmentGeneralTripsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TripPreviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TripPreviewViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null)
            holder.bind(currentItem)
    }

    class TripPreviewViewHolder(private val binding: TripsFragmentGeneralTripsListItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

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

    class TripPreviewComparator : DiffUtil.ItemCallback<Trips>() {
        override fun areItemsTheSame(oldItem: Trips, newItem: Trips): Boolean
            = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Trips, newItem: Trips): Boolean
            = oldItem == newItem
    }
}