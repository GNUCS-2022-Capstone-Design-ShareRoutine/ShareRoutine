package com.example.shareroutine.ui.routine.manage.fresh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shareroutine.R
import com.example.shareroutine.databinding.TodoItemBinding
import com.example.shareroutine.domain.model.Todo

class NewTodoAdapter(private val todos: List<Todo>) : RecyclerView.Adapter<NewTodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(
        val binding: TodoItemBinding,
        todos: List<Todo>
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.todoItemDescription.setOnLongClickListener {
                println("${todos[bindingAdapterPosition]}")

                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return TodoViewHolder(TodoItemBinding.bind(view), todos)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.binding.todoItemDescription.text = todos[position].description
    }

    override fun getItemCount(): Int = todos.size
}