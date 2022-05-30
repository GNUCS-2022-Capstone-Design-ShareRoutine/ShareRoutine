package com.example.shareroutine.ui.routine.manage.fresh

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shareroutine.R
import com.example.shareroutine.databinding.NewRoutineFragmentBinding
import com.example.shareroutine.domain.model.Term
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.time.DayOfWeek
import java.time.LocalTime

class NewRoutineFragment : Fragment() {

    private var _binding: NewRoutineFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var viewModel: NewRoutineViewModel

    private val activityResultCallback = ActivityResultCallback<ActivityResult> {
        when (it.resultCode) {
            RESULT_OK -> {
                when (viewModel.routine.term) {
                    Term.DAILY -> {
                        val time = it?.data?.getSerializableExtra("time") as LocalTime

                        Log.d("Response time", time.toString())
                    }
                    Term.WEEKLY -> {
                        val dayOfWeek = it?.data?.getSerializableExtra("dayOfWeek") as DayOfWeek

                        Log.d("Response dayOfWeek", dayOfWeek.toString())
                    }
                    Term.MONTHLY -> {
                        val day = it?.data?.getIntExtra("day", 0)

                        Log.d("Response day", day.toString())
                    }
                    Term.YEARLY -> {
                        val month = it?.data?.getSerializableExtra("month")

                        Log.d("Response day", month.toString())
                    }
                    Term.NONE -> {}
                }

                val importance = it?.data?.getIntExtra("importance", 0)

                Log.d("Response importance", importance.toString())

                val description = it?.data?.getStringExtra("description")

                Log.d("Response description", description.toString())
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

        setChipGroupListener()
        setRecyclerView()
        setResultLauncher()

        binding.newRoutineConfirm.setOnClickListener {
            if (viewModel.routine.todos.isEmpty()) {
                MaterialAlertDialogBuilder(requireActivity())
                    .setTitle("추가 오류")
                    .setMessage("할 일을 하나 이상 추가해주세요.")
                    .setPositiveButton("확인") { _, _ -> }.show()
            }
        }

        return root
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
        }
    }

    private fun setRecyclerView() {
        val addButtonClickListener = View.OnClickListener {
            val intent = Intent(requireActivity(), AddTodoActivity::class.java)
            intent.putExtra("term", viewModel.routine.term)

            resultLauncher.launch(intent)
        }

        viewModel.todoList.observe(viewLifecycleOwner) {
            binding.newRoutineTodo.apply {
                layoutManager = LinearLayoutManager(requireActivity())
                adapter = ConcatAdapter(NewTodoAdapter(it), AddTodoAdapter(addButtonClickListener))
            }
        }
    }

    private fun setResultLauncher() {
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            activityResultCallback
        )
    }
}