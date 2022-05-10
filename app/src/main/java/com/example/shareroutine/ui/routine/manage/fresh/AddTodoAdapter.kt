package com.example.shareroutine.ui.routine.manage.fresh

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shareroutine.R
import com.example.shareroutine.databinding.AddTodoItemBinding

class AddTodoAdapter : RecyclerView.Adapter<AddTodoAdapter.AddTodoViewHolder>() {

    class AddTodoViewHolder(val binding: AddTodoItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddTodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.add_todo_item, parent, false)
        return AddTodoViewHolder(AddTodoItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: AddTodoViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = 1
}