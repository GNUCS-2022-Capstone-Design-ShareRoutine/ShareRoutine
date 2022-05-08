package com.example.shareroutine.ui.routine.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shareroutine.databinding.RoutineDetailFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

        var adapters = emptyList<RoutineDetailAdapter>()

        with(viewModel) {
            usedRoutines.observe(viewLifecycleOwner) { list ->
                adapters = list.map { RoutineDetailAdapter(it) }
            }
        }

        val recyclerView = binding.routineDetailList
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        val concatAdapterConfig = ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build()
        val concatAdapter = ConcatAdapter(concatAdapterConfig, adapters)
        recyclerView.adapter = concatAdapter

        return root
    }
}