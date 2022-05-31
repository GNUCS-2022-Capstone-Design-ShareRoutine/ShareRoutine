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
import com.example.shareroutine.ui.routine.detail.ExpandableRoutineAdapter
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

        observeRoutines()

        return root
    }

    private fun observeRoutines() {
        val currentUserHeader = RecyclerViewHeaderAdapter("내가 만든 루틴 목록")
        val downloadedHeader = RecyclerViewHeaderAdapter("다운로드받은 루틴 목록")

        viewModel.routineList.observe(viewLifecycleOwner) { list ->
            val uid = FirebaseAuth.getInstance().uid!!

            val currentUserRoutineList = list.filter { it.userId == uid }
            val downloadedRoutineList = list.filter { it.userId != uid }

            val currentUserExpandable = currentUserRoutineList.map {
                ExpandableRoutineAdapter(it)
            }
            val downloadedExpandable = downloadedRoutineList.map {
                ExpandableRoutineAdapter(it)
            }

            val currentAdapter = ConcatAdapter(currentUserExpandable)
            val downloadedAdapter = ConcatAdapter(downloadedExpandable)

            val existAdapter = ConcatAdapter(
                currentUserHeader, currentAdapter,
                downloadedHeader, downloadedAdapter
            )

            binding.existRoutineRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireActivity())
                adapter = existAdapter
            }
        }
    }
}