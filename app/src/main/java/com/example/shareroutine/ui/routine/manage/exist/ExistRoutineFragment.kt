package com.example.shareroutine.ui.routine.manage.exist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shareroutine.databinding.ExistRoutineFragmentBinding
import com.example.shareroutine.ui.adapter.RecyclerViewHeaderAdapter

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

        val myRoutineHeader = RecyclerViewHeaderAdapter("내가 만든 루틴 목록")
        val downloadedRoutineHeader = RecyclerViewHeaderAdapter("다운로드받은 루틴 목록")

        // val myRoutineAdapter = ExpandableRoutineAdapter
        // val downloadedRoutineAdapter = ExpandableRoutineAdapter

        // 목록 가져올 때 사이에 끼워 넣어주기
        val routineAdapter = ConcatAdapter(myRoutineHeader, downloadedRoutineHeader)

        binding.existRoutineRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = routineAdapter
        }

        return root
    }
}