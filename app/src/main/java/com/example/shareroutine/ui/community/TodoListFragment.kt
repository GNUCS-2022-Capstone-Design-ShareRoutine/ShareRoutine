package com.example.shareroutine.ui.community

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shareroutine.databinding.TodoListFragmentBinding

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
        recyclerView.adapter = TodoListAdapter(listOf()) // viewModel 적용 필요

        viewModel.todoList.observe(viewLifecycleOwner) {
            val adapter = binding.todoListRecyclerView.adapter as TodoListAdapter

            adapter.setData(it)
        }

        return root
    }
}