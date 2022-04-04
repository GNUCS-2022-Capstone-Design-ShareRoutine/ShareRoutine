package com.example.shareroutine.ui.routine.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shareroutine.databinding.RoutineMainFragmentBinding

class RoutineMainFragment : Fragment() {

    private var _binding: RoutineMainFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RoutineMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[RoutineMainViewModel::class.java]

        _binding = RoutineMainFragmentBinding.inflate(inflater, container, false)
        val root = binding.root

        return root
    }
}