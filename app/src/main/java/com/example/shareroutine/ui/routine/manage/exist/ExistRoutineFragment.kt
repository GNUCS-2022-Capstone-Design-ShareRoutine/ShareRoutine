package com.example.shareroutine.ui.routine.manage.exist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shareroutine.R
import com.example.shareroutine.databinding.ExistRoutineFragmentBinding

class ExistRoutineFragment : Fragment() {

    private var _binding: ExistRoutineFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ExistRoutineViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ExistRoutineViewModel::class.java]

        _binding = ExistRoutineFragmentBinding.inflate(inflater, container, false)
        val root = binding.root

        return root
    }
}