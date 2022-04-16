package com.example.shareroutine.ui.routine.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shareroutine.R
import com.example.shareroutine.data.model.Routine
import com.example.shareroutine.databinding.RoutineDetailListParentBinding

class RoutineDetailAdapter(private val routineList: List<Routine>) : RecyclerView.Adapter<RoutineDetailAdapter.ViewHolder>() {
    class ViewHolder(val binding: RoutineDetailListParentBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        const val PARENT = 0
        const val CHILD = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.routine_detail_list_parent, parent, false)

        return ViewHolder(RoutineDetailListParentBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val routine = routineList[position]

        holder.binding.routineDetailTitle.text = routine.title
    }

    override fun getItemCount(): Int = routineList.size
}