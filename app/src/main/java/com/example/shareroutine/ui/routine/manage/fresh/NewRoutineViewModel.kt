package com.example.shareroutine.ui.routine.manage.fresh

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.model.Term
import com.example.shareroutine.domain.model.Todo

class NewRoutineViewModel : ViewModel() {
    val routine = Routine("", Term.DAILY, false, emptyList())

    private val list = mutableListOf<Todo>()

    private val _todoList = MutableLiveData<List<Todo>>()
    val todoList: LiveData<List<Todo>> = _todoList

    init {
        _todoList.value = list
    }

    fun setTerm(term: Term) {
        routine.term = term
    }

    fun addTodo(todo: Todo) {
        list.add(todo)
        _todoList.value = list
    }

    fun removeTodo(todo: Todo) {
        list.remove(todo)
        _todoList.value = list
    }
}