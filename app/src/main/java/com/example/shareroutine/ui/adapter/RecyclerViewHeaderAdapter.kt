package com.example.shareroutine.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shareroutine.R
import com.example.shareroutine.databinding.RecyclerViewHeaderBinding

class RecyclerViewHeaderAdapter(val text: String) : RecyclerView.Adapter<RecyclerViewHeaderAdapter.ViewHolder>() {

    class ViewHolder(val binding: RecyclerViewHeaderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerViewHeaderBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_header, parent, false)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.recyclerViewHeaderText.text = text
    }

    override fun getItemCount(): Int = 1
}