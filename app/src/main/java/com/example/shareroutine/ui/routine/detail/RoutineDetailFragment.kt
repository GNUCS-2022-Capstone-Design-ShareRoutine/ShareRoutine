package com.example.shareroutine.ui.routine.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shareroutine.R
import com.example.shareroutine.databinding.RoutineDetailFragmentBinding
import com.example.shareroutine.domain.model.UsedTodo
import com.example.shareroutine.ui.adapter.UsedTodoListAdapter
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoutineDetailFragment : Fragment() {

    private var _binding: RoutineDetailFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RoutineDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[RoutineDetailViewModel::class.java]

        _binding = RoutineDetailFragmentBinding.inflate(inflater, container, false)
        val root = binding.root

        observeUsedTodo()

        return root
    }

    private fun observeUsedTodo() {
        with(viewModel) {
            usedTodos.observe(viewLifecycleOwner) { list ->
                if (list.isNotEmpty()) {
                    val sorted = list.filter {
                        !it.achieved
                    }.sortedByDescending {
                        it.importance
                    }

                    binding.routineDetailList.apply {
                        layoutManager = LinearLayoutManager(requireActivity())
                        adapter = UsedTodoListAdapter(sorted)
                    }

                    configureChartAppearance(sorted)
                }
                else {
                    binding.routineDetailList.apply {
                        adapter = UsedTodoListAdapter(emptyList())
                    }

                    configureChartAppearance(list)
                }
            }
        }
    }

    private fun configureChartAppearance(list: List<UsedTodo>) {
        val entryList = mutableListOf<BarEntry>()

        list.forEach {
            entryList.add(BarEntry(
                list.indexOf(it).toFloat(),
                it.importance.toFloat())
            )
        }

        val labelList = list.map { it.description }

        val barDataSet = BarDataSet(entryList, "중요도")
        barDataSet.setDrawValues(false)
        barDataSet.color = ContextCompat.getColor(
            requireActivity(),
            R.color.share_routine_theme_dark_purple
        )

        val data = BarData(barDataSet)
        data.barWidth = 0.3f

        binding.routineDetailImportance.run {
            this.data = data

            axisRight.isEnabled = false
            description.isEnabled = false
            legend.isEnabled = false

            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.valueFormatter = IndexAxisValueFormatter(labelList)
            xAxis.setDrawGridLines(false)
            xAxis.granularity = 1f

            axisLeft.axisMinimum = 0f
            axisLeft.axisMaximum = 10f
            axisLeft.setDrawGridLines(false)

            animateY(1000)

            setDrawBarShadow(false)
            setMaxVisibleValueCount(5)
            setPinchZoom(false)
            setTouchEnabled(false)

            invalidate()
        }
    }
}