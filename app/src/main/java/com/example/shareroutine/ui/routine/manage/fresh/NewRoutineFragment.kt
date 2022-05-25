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

class NewRoutineFragment : Fragment() {

    private var _binding: NewRoutineFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var viewModel: NewRoutineViewModel

    private val activityResultCallback = ActivityResultCallback<ActivityResult> {
        when (it.resultCode) {
            RESULT_OK -> {
                val resultData = it?.data?.getStringExtra("result")

                Log.d("NewRoutineFragment", resultData!!)
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

        binding.newRoutineTodo.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = ConcatAdapter(NewTodoAdapter(emptyList()), AddTodoAdapter(addButtonClickListener))
        }
    }

    private fun setResultLauncher() {
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            activityResultCallback
        )
    }
}