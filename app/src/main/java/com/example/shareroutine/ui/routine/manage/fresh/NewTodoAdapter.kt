package com.example.shareroutine.ui.routine.manage.fresh

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shareroutine.R
import com.example.shareroutine.databinding.TodoItemBinding
import com.example.shareroutine.domain.model.Todo

class NewTodoAdapter(
    private val todos: List<Todo>,
    private val listener: ItemLongClickListener
    ) : RecyclerView.Adapter<NewTodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root)

    interface ItemLongClickListener {
        fun onItemLongClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return TodoViewHolder(TodoItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.binding.todoItemDescription.text = todos[position].description
        holder.binding.todoItemDescription.setOnLongClickListener {
            listener.onItemLongClick(position)

            true
        }
    }

    override fun getItemCount(): Int = todos.size
}