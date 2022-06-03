package com.example.shareroutine.ui.routine.detail

import androidx.lifecycle.*
import com.example.shareroutine.domain.usecase.routine.GetUsedTodoListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoutineDetailViewModel @Inject constructor(
    getUsedTodoListUseCase: GetUsedTodoListUseCase
) : ViewModel() {
    private val _usedTodos = getUsedTodoListUseCase().asLiveData()
    val usedTodos get() = _usedTodos
}