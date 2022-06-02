package com.example.shareroutine.domain.usecase.routine

import com.example.shareroutine.domain.model.UsedTodo
import com.example.shareroutine.domain.repository.UsedTodoRepository
import javax.inject.Inject

class UpdateUsedTodoUseCase @Inject constructor(
    private val repository: UsedTodoRepository
    ) {
    suspend operator fun invoke(todo: UsedTodo) {
        repository.update(todo)
    }
}