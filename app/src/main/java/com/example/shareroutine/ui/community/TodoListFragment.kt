package com.example.shareroutine.ui.community

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shareroutine.databinding.TodoListFragmentBinding
import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.ui.adapter.TodoListAdapter

class TodoListFragment : Fragment() {

    private var _binding: TodoListFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TodoListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[TodoListViewModel::class.java]

        _binding = TodoListFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.todoListRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.adapter = TodoListAdapter(listOf())
        recyclerView.addItemDecoration(DividerItemDecoration(requireActivity(), 1))

        val bundle = arguments

        if (bundle != null) {
            val routine = bundle.getSerializable("routine") as Routine

            recyclerView.adapter = TodoListAdapter(routine.todos)
        }

        return root
    }
}