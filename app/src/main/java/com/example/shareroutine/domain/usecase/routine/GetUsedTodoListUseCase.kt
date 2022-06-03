package com.example.shareroutine.domain.usecase.routine

import com.example.shareroutine.domain.model.UsedTodo
import com.example.shareroutine.domain.repository.UsedTodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsedTodoListUseCase @Inject constructor(
    private val repository: UsedTodoRepository
) {
    operator fun invoke(): Flow<List<UsedTodo>> {
        return repository.getUsedTodoList()
    }
}