package com.example.shareroutine.ui.routine.manage.fresh

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shareroutine.databinding.NewRoutineFragmentBinding

class NewRoutineFragment : Fragment() {

    private var _binding: NewRoutineFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: NewRoutineViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[NewRoutineViewModel::class.java]

        _binding = NewRoutineFragmentBinding.inflate(inflater, container, false)
        val root = binding.root

        binding.newRoutineTodo.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = ConcatAdapter(NewTodoAdapter(emptyList()), AddTodoAdapter())
        }

        return root
    }
}