package com.example.shareroutine.ui.community

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shareroutine.data.model.Todo
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

        // Test model
        val todo1 = Todo("할 일1")
        val todo2 = Todo("할 일2")
        val todo3 = Todo("할 일3")
        val todo4 = Todo("할 일4")
        val todo5 = Todo("할 일5")

        val recyclerView = binding.todoListRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.adapter = TodoListAdapter(listOf(todo1, todo2, todo3, todo4, todo5)) // viewModel 적용 필요

        viewModel.todoList.observe(viewLifecycleOwner) {
            val adapter = binding.todoListRecyclerView.adapter as TodoListAdapter

            adapter.setData(it)
        }

        return root
    }
}