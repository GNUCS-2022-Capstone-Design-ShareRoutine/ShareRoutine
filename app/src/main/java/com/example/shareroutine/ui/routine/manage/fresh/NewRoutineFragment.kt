package com.example.shareroutine.ui.routine.manage.fresh

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shareroutine.R
import com.example.shareroutine.databinding.NewRoutineFragmentBinding
import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.model.Term
import com.example.shareroutine.domain.model.Todo
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.Month

@AndroidEntryPoint
class NewRoutineFragment : Fragment() {

    private var _binding: NewRoutineFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var viewModel: NewRoutineViewModel

    private val activityResultCallback = ActivityResultCallback<ActivityResult> {
        when (it.resultCode) {
            RESULT_OK -> {
                val todo = Todo(importance = 0, description = "")

                val importance = it?.data?.getIntExtra("importance", 0)
                importance?.let { todo.importance = importance }

                val description = it?.data?.getStringExtra("description")
                description?.let { todo.description = description }

                when (viewModel.routine.term) {
                    Term.DAILY -> {
                        val time = it?.data?.getSerializableExtra("time") as LocalTime
                        todo.time = time
                    }
                    Term.WEEKLY -> {
                        val dayOfWeek = it?.data?.getSerializableExtra("dayOfWeek") as DayOfWeek
                        todo.dayOfWeek = dayOfWeek
                    }
                    Term.MONTHLY -> {
                        val day = it?.data?.getIntExtra("day", 0)
                        todo.day = day
                    }
                    Term.YEARLY -> {
                        val month = it?.data?.getSerializableExtra("month") as Month
                        todo.month = month
                    }
                    Term.NONE -> {}
                }

                viewModel.addTodo(todo)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[NewRoutineViewModel::class.java]

        _binding = NewRoutineFragmentBinding.inflate(inflater, container, false)
        val root = binding.root

        observeRoutine()
        observeTodoList()

        setActivityResultLauncher()
        setOnFragmentResult()

        setChipGroupListener()
        setNewRoutineConfirmButton()

        return root
    }

    private fun observeRoutine() {
        viewModel.routine1.observe(viewLifecycleOwner) {
            binding.newRoutineTitleEdit.text = SpannableStringBuilder(it.name)

            when (it.term) {
                Term.DAILY -> binding.newRoutineTermDaily.isChecked = true
                Term.WEEKLY -> binding.newRoutineTermWeekly.isChecked = true
                Term.MONTHLY -> binding.newRoutineTermMonthly.isChecked = true
                Term.YEARLY -> binding.newRoutineTermYearly.isChecked = true
                Term.NONE -> {}
            }
        }
    }

    private fun observeTodoList() {
        viewModel.todoList.observe(viewLifecycleOwner) {
            val listener = object : NewTodoAdapter.ItemLongClickListener {
                override fun onItemLongClick(position: Int) {
                    MaterialAlertDialogBuilder(requireActivity())
                        .setMessage("할 일을 삭제하시겠습니까?")
                        .setPositiveButton("확인") { _, _ ->
                            viewModel.removeTodo(it[position])
                        }
                        .setNegativeButton("취소") { _, _ -> }
                        .show()
                }
            }

            binding.newRoutineTodo.apply {
                layoutManager = LinearLayoutManager(requireActivity())
                adapter = ConcatAdapter(NewTodoAdapter(it, listener), AddTodoAdapter(onButtonClickListener()))
            }
        }
    }

    private fun onButtonClickListener() = View.OnClickListener {
        val intent = Intent(requireActivity(), AddTodoActivity::class.java)
        intent.putExtra("term", viewModel.routine.term)

        resultLauncher.launch(intent)
    }

    private fun setActivityResultLauncher() {
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            activityResultCallback
        )
    }

    private fun setOnFragmentResult() {
        setFragmentResultListener("updateRoutine") { _, bundle ->
            bundle.getSerializable("routine")?.let {
                viewModel.setRoutine(it as Routine)
            }
        }
    }

    private fun setChipGroupListener() {
        binding.newRoutineTerm.setOnCheckedStateChangeListener { _, checkedIds ->
            val term = when (checkedIds[0]) {
                R.id.new_routine_term_daily -> Term.DAILY
                R.id.new_routine_term_weekly -> Term.WEEKLY
                R.id.new_routine_term_monthly -> Term.MONTHLY
                R.id.new_routine_term_yearly -> Term.YEARLY
                else -> Term.NONE
            }

            viewModel.setTerm(term)
            viewModel.emptyTodos()
        }
    }

    private fun setNewRoutineConfirmButton() {
        binding.newRoutineConfirm.setOnClickListener {
            if (binding.newRoutineTitleEdit.text?.isBlank() == true) {
                MaterialAlertDialogBuilder(requireActivity())
                    .setTitle("입력 오류")
                    .setMessage("루틴 이름을 입력해주세요.")
                    .setPositiveButton("확인") { _, _ -> }.show()
            }
            else if (viewModel.routine.todos.isEmpty()) {
                MaterialAlertDialogBuilder(requireActivity())
                    .setTitle("추가 오류")
                    .setMessage("할 일을 하나 이상 추가해주세요.")
                    .setPositiveButton("확인") { _, _ -> }.show()
            }
            else {
                viewModel.routine.name = binding.newRoutineTitleEdit.text.toString()

                viewModel.addRoutine().observe(viewLifecycleOwner) {
                    if (it) {
                        Toast.makeText(
                            requireActivity(),
                            "루틴 작성을 완료합니다.",
                            Toast.LENGTH_LONG
                        ).show()
                        requireActivity().finish()
                    }
                    else {
                        Toast.makeText(
                            requireActivity(),
                            "루틴을 작성할 수 없습니다!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}