package com.example.shareroutine.ui.community

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shareroutine.R
import com.example.shareroutine.data.model.Todo
import com.example.shareroutine.databinding.TodoItemBinding

class TodoListAdapter(private var todoList: List<Todo>) : RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {
    class ViewHolder(val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)

        return ViewHolder(TodoItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo = todoList[position]

        holder.binding.todoItemDescription.text = todo.description
    }

    override fun getItemCount(): Int = todoList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<Todo>) {
        todoList = newList
        notifyDataSetChanged()
    }
}