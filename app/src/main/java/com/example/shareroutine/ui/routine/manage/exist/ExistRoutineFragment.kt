package com.example.shareroutine.ui.routine.manage.exist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shareroutine.R
import com.example.shareroutine.databinding.ExistRoutineFragmentBinding
import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.ui.adapter.RecyclerViewHeaderAdapter
import com.example.shareroutine.ui.adapter.ExpandableRoutineAdapter
import com.example.shareroutine.ui.routine.manage.RoutineManageActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
                val items = arrayOf("선택", "수정", "삭제")

                MaterialAlertDialogBuilder(requireActivity())
                    .setTitle("원하는 동작을 선택하세요")
                    .setItems(items) { _, which ->
                        when (items[which]) {
                            "선택" -> onSelectListener(view, routine)
                            "수정" -> onUpdateListener(routine)
                            "삭제" -> onDeleteListener(routine)
                        }
                    }.show()

                true
            }

            ExpandableRoutineAdapter(routine, listener)
        }

        return ConcatAdapter(expandable)
    }

    private fun onSelectListener(view: View?, routine: Routine) {
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
    }

    private fun onUpdateListener(routine: Routine) {
        val bundle = Bundle()
        bundle.putSerializable("routine", routine)

        setFragmentResult("updateRoutine", bundle)

        val parent = requireActivity() as RoutineManageActivity
        parent.onBackPressed()
    }

    private fun onDeleteListener(routine: Routine) {
        viewModel.deleteRoutine(routine).observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(
                    requireActivity(),
                    "루틴을 삭제했습니다!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else {
                Toast.makeText(
                    requireActivity(),
                    "루틴을 삭제할 수 없습니다!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setRoutineButton() {
        binding.existRoutineButton.setOnClickListener {
            viewModel.useRoutine().observe(viewLifecycleOwner) {
                if (it) {
                    Toast.makeText(
                        requireActivity(),
                        "선택한 루틴을 알맞게 적용합니다!",
                        Toast.LENGTH_SHORT
                    ).show()
                    requireActivity().finish()
                }
                else {
                    Toast.makeText(
                        requireActivity(),
                        "선택한 루틴을 적용할 수 없습니다!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}