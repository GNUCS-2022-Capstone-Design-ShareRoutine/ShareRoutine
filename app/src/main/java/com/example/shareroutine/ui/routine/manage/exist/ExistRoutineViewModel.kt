package com.example.shareroutine.ui.routine.manage.exist

import androidx.lifecycle.*
import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.usecase.routine.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExistRoutineViewModel @Inject constructor(
    getRoutineListUseCase: GetRoutineListUseCase,
    private val updateRoutineUseCase: UpdateRoutineUseCase,
    private val deleteRoutineInLocalUseCase: DeleteRoutineInLocalUseCase,
    private val getUsedTodoListByRoutineUseCase: GetUsedTodoListByRoutineUseCase,
    private val insertUsedTodoUseCase: InsertUsedTodoUseCase,
    private val deleteUsedTodoUseCase: DeleteUsedTodoUseCase
) : ViewModel() {
    val routineList: LiveData<List<Routine>> = getRoutineListUseCase().asLiveData()
    val selectedRoutineList: MutableList<Routine> = mutableListOf()

    fun useRoutine(): LiveData<Boolean> {
        val isSuccessful = MutableLiveData<Boolean>()

        viewModelScope.launch {
            selectedRoutineList.map {
                if (!it.isUsed) {
                    it.isUsed = true

                    updateRoutineUseCase(it)

                    it.todos.map { todo -> insertUsedTodoUseCase(todo, it) }
                }
                else {
                    it.isUsed = false

                    updateRoutineUseCase(it)

                    val usedTodoList = getUsedTodoListByRoutineUseCase(it)

                    usedTodoList.map { todo ->
                        deleteUsedTodoUseCase(todo)
                    }
                }
            }

            isSuccessful.value = true
        }

        return isSuccessful
    }

    fun deleteRoutine(routine: Routine): LiveData<Boolean> {
        val isSuccessful = MutableLiveData<Boolean>()

        viewModelScope.launch {
            deleteRoutineInLocalUseCase(routine)

            isSuccessful.value = true
        }

        return isSuccessful
    }
}