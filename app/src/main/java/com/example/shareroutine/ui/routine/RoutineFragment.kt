package com.example.shareroutine.ui.routine

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shareroutine.databinding.RoutineFragmentBinding

class RoutineFragment : Fragment() {

    private var _binding: RoutineFragmentBinding? = null
    private val binding get() =_binding!!

    private lateinit var viewModel: RoutineViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[RoutineViewModel::class.java]

        _binding = RoutineFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Observing below

        return root
    }
}