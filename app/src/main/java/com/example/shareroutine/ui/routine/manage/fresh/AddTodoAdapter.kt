package com.example.shareroutine.ui.routine.manage.fresh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shareroutine.R
import com.example.shareroutine.databinding.AddTodoItemBinding

class AddTodoAdapter(private val listener: View.OnClickListener) : RecyclerView.Adapter<AddTodoAdapter.AddTodoViewHolder>() {

    class AddTodoViewHolder(val binding: AddTodoItemBinding, listener: View.OnClickListener): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.newTodoButton.setOnClickListener(listener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddTodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.add_todo_item, parent, false)
        return AddTodoViewHolder(AddTodoItemBinding.bind(view), listener)
    }

    override fun onBindViewHolder(holder: AddTodoViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = 1
}