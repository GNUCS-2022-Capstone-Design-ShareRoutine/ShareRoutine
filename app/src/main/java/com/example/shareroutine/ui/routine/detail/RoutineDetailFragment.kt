package com.example.shareroutine.ui.routine.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shareroutine.data.model.Routine
import com.example.shareroutine.data.model.RoutineTodo
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

        // Temporary values for testing
        val todo1 = RoutineTodo("설명 1")
        val todo2 = RoutineTodo("설명 2")
        val todo3 = RoutineTodo("설명 3")
        val todo4 = RoutineTodo("설명 4")
        val todo5 = RoutineTodo("설명 5")

        val todoList = listOf(todo1, todo2, todo3, todo4, todo5)

        val routine1 = Routine("루틴 1", todoList)
        val routine2 = Routine("루틴 2", todoList)
        val routine3 = Routine("루틴 3", todoList)

        val routineList = listOf(routine1, routine2, routine3)

        val adapters: List<RoutineDetailAdapter> = routineList.map {
            RoutineDetailAdapter(it)
        }

        val recyclerView = binding.routineDetailList
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        val concatAdapterConfig = ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build()
        val concatAdapter = ConcatAdapter(concatAdapterConfig, adapters)
        recyclerView.adapter = concatAdapter

        return root
    }
}