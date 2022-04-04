package com.example.shareroutine.ui.routine.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shareroutine.databinding.RoutineDetailFragmentBinding

class RoutineDetailFragment : Fragment() {

    private var _binding: RoutineDetailFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RoutineDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[RoutineDetailViewModel::class.java]

        _binding = RoutineDetailFragmentBinding.inflate(inflater, container, false)
        val root = binding.root

        return root
    }
}