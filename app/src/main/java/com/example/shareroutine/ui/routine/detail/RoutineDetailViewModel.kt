package com.example.shareroutine.ui.routine.detail

import androidx.lifecycle.*
import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.usecase.GetUsedRoutineListUseCase
import com.example.shareroutine.domain.usecase.InsertRoutineUseCase
import kotlinx.coroutines.launch

class RoutineDetailViewModel(
    getUsedRoutineListUseCase: GetUsedRoutineListUseCase,
    val insertRoutineUseCase: InsertRoutineUseCase
) : ViewModel() {
    private val _usedRoutines = getUsedRoutineListUseCase().asLiveData(viewModelScope.coroutineContext)
    val usedRoutines: LiveData<List<Routine>> get() = _usedRoutines

    fun addRoutine(routine: Routine) = viewModelScope.launch {
        insertRoutineUseCase(routine)
    }
}