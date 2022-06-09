package com.example.shareroutine.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shareroutine.R
import com.example.shareroutine.databinding.RecyclerViewHeaderBinding
import com.example.shareroutine.domain.model.Routine

class RoutineAdapter(
    private val routines: List<Routine>,
    private val listener: ItemClickListener
    ) : RecyclerView.Adapter<RoutineAdapter.ViewHolder>() {

    class ViewHolder(val binding: RecyclerViewHeaderBinding) : RecyclerView.ViewHolder(binding.root)

    interface ItemClickListener {
        fun onItemClick(holder: ViewHolder, position: Int, holders: List<ViewHolder>)
    }

    private val holders = mutableListOf<ViewHolder>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_view_header, parent,
            false
        )

        val holder = ViewHolder(RecyclerViewHeaderBinding.bind(view))

        holders.add(holder)

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val routine = routines[position]

        holder.binding.recyclerViewHeaderText.text = routine.name
        holder.binding.recyclerViewHeaderText.setOnClickListener {
            listener.onItemClick(holder, position, holders)
        }
    }

    override fun getItemCount(): Int = routines.size
}