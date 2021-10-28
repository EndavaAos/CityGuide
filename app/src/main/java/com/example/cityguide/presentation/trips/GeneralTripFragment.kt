package com.example.cityguide.presentation.trips

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import androidx.lifecycle.LiveData
import com.example.cityguide.R
import com.example.cityguide.data.db.entity.Trip
import com.example.cityguide.databinding.FragmentGeneralTripBinding
import dagger.android.support.AndroidSupportInjection

abstract class GeneralTripFragment : Fragment(R.layout.fragment_general_trip) {

    private val expandableButtonAnimationDuration = 500L
    private val expandableButtonAnimationRotation = 180f

    private var isListExpanded = true

    private var _binding: FragmentGeneralTripBinding? = null
    private val binding get() = _binding!!

    abstract val title: String
    abstract val observableData: LiveData<List<Trip>>
    abstract val errorScreen: Fragment

    private val listScreen = TripsPreviewList()

    private fun initializeListFragment() {
        childFragmentManager.commit {
            add(R.id.trips_list, errorScreen)
            setReorderingAllowed(true)
        }

        listScreen.setObservable(observableData)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initializeListFragment()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeBinding(view)
    }

    private fun initializeBinding(view: View) {
        _binding = FragmentGeneralTripBinding.bind(view)

        binding.apply {
            tripsCategory.text = title

            observableData.observe(viewLifecycleOwner) {
                if (it.isEmpty()) {
                    setListFragment(errorScreen)
                } else {
                    setListFragment(listScreen)
                }
            }

            expandableButton.setOnClickListener {
                handleExpandCollapse(it, tripsList)
            }
        }
    }

    private fun handleExpandCollapse(button: View, tripsList: FragmentContainerView) {
        animateExpandableButton(button)
        expandCollapse(tripsList)
    }

    private fun animateExpandableButton(button: View) {
        val rotate = ObjectAnimator
            .ofFloat(button, View.ROTATION, expandableButtonAnimationRotation)
            .setDuration(expandableButtonAnimationDuration)

        rotate.interpolator = AccelerateDecelerateInterpolator()
        rotate.repeatMode = ObjectAnimator.REVERSE

        val animation = AnimatorSet()
        animation.playSequentially(
            rotate
        )

        animation.start()
    }

    private fun expandCollapse(tripsList: FragmentContainerView) {
        // TODO: Animate expanding/collapsing

        isListExpanded = !isListExpanded

        tripsList.visibility = if (isListExpanded) View.VISIBLE else View.GONE
    }

    private fun setListFragment(listFragment: Fragment) {
        childFragmentManager.commit {
            replace(R.id.trips_list, listFragment)
            setReorderingAllowed(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}