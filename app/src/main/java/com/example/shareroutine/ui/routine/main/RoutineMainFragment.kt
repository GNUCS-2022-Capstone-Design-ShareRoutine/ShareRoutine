package com.example.shareroutine.ui.routine.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.shareroutine.R
import com.example.shareroutine.databinding.RoutineMainFragmentBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoutineMainFragment : Fragment() {

    private var _binding: RoutineMainFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RoutineMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[RoutineMainViewModel::class.java]

        _binding = RoutineMainFragmentBinding.inflate(inflater, container, false)
        val root = binding.root

        setProgressBar()
        observeUsedTodo()

        return root
    }

    private fun setProgressBar() {
        binding.routineMainProgressChart.backgroundProgressBarWidth = 15f

        binding.routineMainProgressChart.backgroundProgressBarColor = ContextCompat.getColor(
            requireActivity(), R.color.share_routine_theme_progress_bar_color
        )
        binding.routineMainProgressChart.progressBarColorStart = ContextCompat.getColor(
            requireActivity(), R.color.share_routine_theme_cream_pink
        )
        binding.routineMainProgressChart.progressBarColorEnd = ContextCompat.getColor(
            requireActivity(), R.color.share_routine_theme_cream_pink_light
        )
    }

    // 시간 변화에 따라 업데이트하도록?

    private fun observeUsedTodo() {
        viewModel.usedTodo.observe(viewLifecycleOwner) { list ->
            if (list.isNotEmpty()) {
                val achieved = list.filter { it.achieved }
                val progressText = "${(achieved.size.toFloat() / list.size * 100).toInt()}%"

                binding.routineMainProgressChart.setProgressWithAnimation(
                    achieved.size.toFloat() / list.size * 100,
                    1000
                )
                binding.routineMainProgressText.text = progressText

                val notAchieved = list.filter {
                    !it.achieved
                }.sortedByDescending {
                    it.importance
                }

                if (notAchieved.isNotEmpty()) {
                    binding.routineMainTextItem.text = notAchieved[0].description
                    viewModel.currentFirstTodo = notAchieved[0]
                }
                else {
                    binding.routineMainTextItem.text = "-"
                }

                setAchieveCurrentTodo()
            }
            else {
                binding.routineMainProgressText.text = "-"
                binding.routineMainTextItem.text = "-"
                binding.routineMainProgressChart.setProgressWithAnimation(
                    0f, 1000
                )
            }
        }
    }

    private fun setAchieveCurrentTodo() {
        val currentTodo = binding.routineMainTextItem.text.toString()

        if (currentTodo != "-") {
            binding.routineMainTextItem.setOnClickListener {
                MaterialAlertDialogBuilder(requireActivity())
                    .setMessage("현재 할 일인 \"$currentTodo\"을(를) 끝내셨나요?")
                    .setPositiveButton("확인") { _, _ ->
                        viewModel.setAchieved().observe(viewLifecycleOwner) {
                            if (it) {
                                Toast.makeText(requireActivity(),
                                    "할 일을 완료했습니다!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            else {
                                Toast.makeText(requireActivity(),
                                    "할 일 완료에 실패하였습니다!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                    .setNegativeButton("취소") { _, _ -> }
                    .show()
            }
        }
        else {
            binding.routineMainTextItem.setOnClickListener(null)
        }
    }
}