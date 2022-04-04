package com.example.shareroutine.ui.routine

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.viewpager2.widget.ViewPager2
import com.example.shareroutine.databinding.RoutineFragmentBinding

class RoutineFragment : Fragment() {

    private var _binding: RoutineFragmentBinding? = null
    private val binding get() =_binding!!

    private lateinit var backPressedCallback: OnBackPressedCallback
    private lateinit var viewPager:  ViewPager2
    private lateinit var viewModel: RoutineViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[RoutineViewModel::class.java]

        _binding = RoutineFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewPager = binding.routinePager
        viewPager.adapter = RoutinePagerAdapter(this)

        // Observing below

        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (viewPager.currentItem != 0) {
                    viewPager.currentItem--
                }
                else {
                    requireActivity().finish()
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }
}