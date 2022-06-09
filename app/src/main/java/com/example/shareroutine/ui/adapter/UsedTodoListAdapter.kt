package com.example.shareroutine.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shareroutine.R
import com.example.shareroutine.databinding.UsedTodoItemBinding
import com.example.shareroutine.domain.model.Term
import com.example.shareroutine.domain.model.UsedTodo
import java.time.DayOfWeek
import java.time.format.DateTimeFormatter

class UsedTodoListAdapter(private var todoList: List<UsedTodo>) : RecyclerView.Adapter<UsedTodoListAdapter.ViewHolder>() {
    class ViewHolder(val binding: UsedTodoItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.used_todo_item, parent, false)

        return ViewHolder(UsedTodoItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo = todoList[position]

        val time = when (todo.term) {
            Term.DAILY -> {
                val formatter = DateTimeFormatter.ofPattern("a hh:mm")
                todo.dateTime.format(formatter)
            }
            Term.WEEKLY -> {
                val dayOfWeekText = when (todo.dateTime.dayOfWeek) {
                    DayOfWeek.MONDAY -> "월"
                    DayOfWeek.TUESDAY -> "화"
                    DayOfWeek.WEDNESDAY -> "수"
                    DayOfWeek.THURSDAY -> "목"
                    DayOfWeek.FRIDAY -> "금"
                    DayOfWeek.SATURDAY -> "토"
                    DayOfWeek.SUNDAY -> "일"
                    null -> "-"
                }

                "${dayOfWeekText}요일"
            }
            Term.MONTHLY -> "${todo.dateTime.dayOfMonth}일"
            Term.YEARLY -> "${todo.dateTime.month.value}월"
            Term.NONE -> ""
        }

        holder.binding.usedTodoTime.text = time
        holder.binding.usedTodoImportance.text = todo.importance.toString()
        holder.binding.usedTodoDescription.text = todo.description
    }

    override fun getItemCount(): Int = todoList.size
}