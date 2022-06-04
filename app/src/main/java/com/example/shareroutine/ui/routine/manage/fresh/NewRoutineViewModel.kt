package com.example.shareroutine.ui.routine.manage.fresh

import androidx.lifecycle.*
import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.model.Term
import com.example.shareroutine.domain.model.Todo
import com.example.shareroutine.domain.usecase.routine.GetRoutineByNameUseCase
import com.example.shareroutine.domain.usecase.routine.InsertRoutineUseCase
import com.example.shareroutine.domain.usecase.routine.UpdateRoutineUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewRoutineViewModel @Inject constructor(
    private val getRoutineByNameUseCase: GetRoutineByNameUseCase,
    private val insertRoutineUseCase: InsertRoutineUseCase,
    private val updateRoutineUseCase: UpdateRoutineUseCase
) : ViewModel() {
    val routine = Routine(name = "", term = Term.DAILY, isUsed = false, todos = emptyList())

    private val _routine = MutableLiveData<Routine>()
    val routine1: LiveData<Routine> get() = _routine

    private val list = mutableListOf<Todo>()

    private val _todoList = MutableLiveData<List<Todo>>(list)
    val todoList: LiveData<List<Todo>> = _todoList

    init {
        val currentUser = FirebaseAuth.getInstance().currentUser
        routine.userId = currentUser?.uid.toString()
    }

    fun setTerm(term: Term) {
        routine.term = term
    }

    fun setRoutine(newRoutine: Routine) {
        _routine.value = newRoutine

        emptyTodos()
        newRoutine.todos.map { addTodo(it) }

        routine.id = _routine.value?.id
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

    fun addRoutine(): LiveData<Boolean> {
        val isInsertSuccessful = MutableLiveData<Boolean>()

        viewModelScope.launch {
            val check = getRoutineByNameUseCase(routine.name)

            if (check == null) {
                insertRoutineUseCase(routine)
            }
            else {
                updateRoutineUseCase(routine)
            }

            isInsertSuccessful.value = true
        }

        return isInsertSuccessful
    }
}