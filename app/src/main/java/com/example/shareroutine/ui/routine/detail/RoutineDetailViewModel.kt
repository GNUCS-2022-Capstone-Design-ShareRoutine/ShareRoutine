package com.example.shareroutine.ui.routine.detail

import androidx.lifecycle.*
import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.usecase.GetUsedRoutineListUseCase
import com.example.shareroutine.domain.usecase.routine.InsertRoutineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineDetailViewModel @Inject constructor(
    getUsedRoutineListUseCase: GetUsedRoutineListUseCase,
    private val insertRoutineUseCase: InsertRoutineUseCase
) : ViewModel() {
    private val _usedRoutines = getUsedRoutineListUseCase().onStart { emptyList<Routine>() }.asLiveData()
    val usedRoutines: LiveData<List<Routine>> get() = _usedRoutines

    fun insertNewRoutine(routine: Routine) {
        viewModelScope.launch {
            insertRoutineUseCase(routine)
        }
    }
}