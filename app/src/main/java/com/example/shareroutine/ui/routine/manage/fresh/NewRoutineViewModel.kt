package com.example.shareroutine.ui.routine.manage.fresh

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.model.Term
import com.example.shareroutine.domain.model.Todo
import com.example.shareroutine.domain.usecase.routine.GetRoutineByNameUseCase
import com.example.shareroutine.domain.usecase.routine.InsertRoutineUseCase
import com.example.shareroutine.domain.usecase.routine.InsertUsedTodoUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewRoutineViewModel @Inject constructor(
    private val getRoutineByNameUseCase: GetRoutineByNameUseCase,
    private val insertRoutineUseCase: InsertRoutineUseCase,
    private val insertUsedTodoUseCase: InsertUsedTodoUseCase
) : ViewModel() {
    val routine = Routine(name = "", term = Term.DAILY, isUsed = false, todos = emptyList())

    private val list = mutableListOf<Todo>()

    private val _todoList = MutableLiveData<List<Todo>>()
    val todoList: LiveData<List<Todo>> = _todoList

    init {
        val currentUser = FirebaseAuth.getInstance().currentUser
        routine.userId = currentUser?.uid.toString()

        _todoList.value = list
    }

    fun setTerm(term: Term) {
        routine.term = term
    }

    fun addTodo(todo: Todo) {
        list.add(todo)
        _todoList.value = list
        routine.todos = list
    }

    fun removeTodo(todo: Todo) {
        list.remove(todo)
        _todoList.value = list
        routine.todos = list
    }

    fun emptyTodos() {
        list.clear()
        _todoList.value = list
        routine.todos = list
    }

    fun checkRoutine() {
        Log.d("LogRoutine", routine.toString())
    }

    fun addRoutine(): LiveData<Boolean> {
        val isInsertSuccessful = MutableLiveData<Boolean>()

        viewModelScope.launch {
            val check = getRoutineByNameUseCase(routine.name)

            if (check == null) {
                insertRoutineUseCase(routine)

                isInsertSuccessful.value = true
            } else {
                isInsertSuccessful.value = false
            }
        }

        return isInsertSuccessful
    }
}