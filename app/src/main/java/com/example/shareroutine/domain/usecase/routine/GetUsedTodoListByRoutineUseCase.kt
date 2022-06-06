package com.example.shareroutine.domain.usecase.routine

import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.model.UsedTodo
import com.example.shareroutine.domain.repository.UsedTodoRepository
import javax.inject.Inject

class GetUsedTodoListByRoutineUseCase @Inject constructor(
    private val repository: UsedTodoRepository
    ) {
    suspend operator fun invoke(routine: Routine): List<UsedTodo> {
        return repository.getUsedTodoListByRoutine(routine)
    }
}