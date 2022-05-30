package com.example.shareroutine.domain.usecase.routine

import com.example.shareroutine.domain.model.UsedTodo
import com.example.shareroutine.domain.repository.UsedTodoRepository
import javax.inject.Inject

class InsertUsedTodoUseCase @Inject constructor(private val repository: UsedTodoRepository) {
    suspend operator fun invoke(usedTodo: UsedTodo) {
        repository.insert(usedTodo)
    }
}