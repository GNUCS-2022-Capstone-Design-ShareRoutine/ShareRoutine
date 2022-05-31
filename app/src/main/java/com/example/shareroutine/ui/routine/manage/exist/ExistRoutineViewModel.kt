package com.example.shareroutine.ui.routine.manage.exist

import androidx.lifecycle.*
import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.usecase.routine.GetRoutineListUseCase
import com.example.shareroutine.domain.usecase.routine.InsertUsedTodoUseCase
import com.example.shareroutine.domain.usecase.routine.UpdateRoutineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExistRoutineViewModel @Inject constructor(
    getRoutineListUseCase: GetRoutineListUseCase,
    private val updateRoutineUseCase: UpdateRoutineUseCase,
    private val insertUsedTodoUseCase: InsertUsedTodoUseCase
) : ViewModel() {
    val routineList: LiveData<List<Routine>> = getRoutineListUseCase().asLiveData()
    val selectedRoutineList: MutableList<Routine> = mutableListOf()

    fun useRoutine(): LiveData<Boolean> {
        val isSuccessful = MutableLiveData<Boolean>()

        viewModelScope.launch {
            selectedRoutineList.map {
                it.isUsed = true

                updateRoutineUseCase(it)

                it.todos.map { todo -> insertUsedTodoUseCase(todo, it) }
            }

            isSuccessful.value = true
        }

        return isSuccessful
    }
}