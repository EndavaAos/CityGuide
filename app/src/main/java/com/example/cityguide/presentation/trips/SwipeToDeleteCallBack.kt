package com.example.cityguide.presentation.trips

import android.content.Context
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.cityguide.R
import com.example.cityguide.presentation.trips.tripSegment.GeneralTripViewModel

class SwipeToDeleteCallBack(
    dragDirs: Int,
    swipeDirs: Int,
    context: Context,
    private val tripAdapter: TripPreviewAdapter,
    private val viewModel: GeneralTripViewModel
) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {
    private val icon = ContextCompat.getDrawable(context, R.drawable.ic_trash)!!
    private val background = ContextCompat.getDrawable(context, R.drawable.gradient_delete)!!

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val student = tripAdapter.currentList[viewHolder.adapterPosition]
        viewModel.onTripSwiped(student)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(
            c,
            recyclerView,
            viewHolder,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
        val itemView = viewHolder.itemView
        val intrinsicHeight = icon.intrinsicHeight
        val intrinsicWidth = icon.intrinsicWidth

        val top = itemView.top + (itemView.height - intrinsicHeight) / 2
        val left = itemView.width - intrinsicWidth - (itemView.height - intrinsicHeight) / 2
        val right = left + intrinsicHeight
        val bottom = top + intrinsicHeight

        when {
            dX < 0 -> { // swiping to the left
                background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                icon.setBounds(left, top, right, bottom)
            }
            dX > 0 -> { // swiping to the right
                background.setBounds(itemView.left + dX.toInt(), itemView.top, itemView.left, itemView.bottom)
                icon.setBounds(top, top, top, bottom)
            }
            else -> { // view is unSwiped
                background.setBounds(0, 0, 0, 0)
                icon.setBounds(0, 0, 0, 0)
            }
        }
        background.draw(c)
        icon.draw(c)
    }
}