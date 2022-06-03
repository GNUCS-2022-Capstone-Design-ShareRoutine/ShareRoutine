package com.example.shareroutine.ui.routine.main

import androidx.lifecycle.*
import com.example.shareroutine.domain.model.UsedTodo
import com.example.shareroutine.domain.usecase.routine.GetUsedTodoListUseCase
import com.example.shareroutine.domain.usecase.routine.UpdateUsedTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineMainViewModel @Inject constructor(
    getUsedTodoListUseCase: GetUsedTodoListUseCase,
    private val updateUsedTodoUseCase: UpdateUsedTodoUseCase
) : ViewModel() {
    private val _usedTodos = getUsedTodoListUseCase().asLiveData()
    val usedTodo get() = _usedTodos

    lateinit var currentFirstTodo: UsedTodo

    fun setAchieved(): LiveData<Boolean> {
        println("ViewModel: $currentFirstTodo")
        val result = MutableLiveData<Boolean>()
        currentFirstTodo.achieved = true

        viewModelScope.launch {
            updateUsedTodoUseCase(currentFirstTodo)

            result.value = true
        }

        return result
    }
}