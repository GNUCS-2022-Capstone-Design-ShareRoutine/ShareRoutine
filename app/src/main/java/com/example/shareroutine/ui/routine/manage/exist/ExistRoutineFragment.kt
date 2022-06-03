package com.example.shareroutine.ui.routine.manage.exist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shareroutine.R
import com.example.shareroutine.databinding.ExistRoutineFragmentBinding
import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.ui.adapter.RecyclerViewHeaderAdapter
import com.example.shareroutine.ui.adapter.ExpandableRoutineAdapter
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

        setAdapter()
        setRoutineButton()

        return root
    }

    // 사용 해제 처리 구현

    private fun setAdapter() {
        val currentUserHeader = RecyclerViewHeaderAdapter("내가 만든 루틴 목록")
        val downloadedHeader = RecyclerViewHeaderAdapter("다운로드받은 루틴 목록")

        viewModel.routineList.observe(viewLifecycleOwner) { list ->
            val uid = FirebaseAuth.getInstance().uid!!

            val currentUserRoutineList = list.filter { it.userId == uid }
            val downloadedRoutineList = list.filter { it.userId != uid }

            val currentAdapter = createConcatAdapter(currentUserRoutineList)
            val downloadedAdapter = createConcatAdapter(downloadedRoutineList)

            val existAdapter = ConcatAdapter(
                currentUserHeader, currentAdapter,
                downloadedHeader, downloadedAdapter
            )

            binding.existRoutineRecyclerView.apply {
                itemAnimator = null
                layoutManager = LinearLayoutManager(requireActivity())
                adapter = existAdapter
            }
        }
    }

    private fun createConcatAdapter(routines: List<Routine>): ConcatAdapter {
        val expandable = routines.map { routine ->
            val listener = View.OnLongClickListener { view ->
                if (viewModel.selectedRoutineList.find { it == routine } == null) {
                    viewModel.selectedRoutineList.add(routine)

                    view?.setBackgroundColor(ContextCompat.getColor(
                        requireActivity(),
                        R.color.share_routine_theme_cream_pink_light
                    ))

                    Toast.makeText(
                        requireActivity(),
                        "루틴을 선택합니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else {
                    viewModel.selectedRoutineList.remove(routine)

                    view?.setBackgroundColor(ContextCompat.getColor(
                        requireActivity(),
                        R.color.white
                    ))

                    Toast.makeText(
                        requireActivity(),
                        "루틴 선택을 해제합니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                true
            }

            ExpandableRoutineAdapter(routine, listener)
        }

        return ConcatAdapter(expandable)
    }

    private fun setRoutineButton() {
        binding.existRoutineButton.setOnClickListener {
            viewModel.useRoutine().observe(viewLifecycleOwner) {
                if (it) {
                    Toast.makeText(
                        requireActivity(),
                        "선택한 루틴을 사용합니다!",
                        Toast.LENGTH_SHORT
                    ).show()
                    requireActivity().finish()
                }
                else {
                    Toast.makeText(
                        requireActivity(),
                        "선택한 루틴을 사용할 수 없습니다!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}