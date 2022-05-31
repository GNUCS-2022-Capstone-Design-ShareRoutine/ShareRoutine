package com.example.shareroutine.domain.usecase.routine

import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.model.Todo
import com.example.shareroutine.domain.model.UsedTodo
import com.example.shareroutine.domain.repository.UsedTodoRepository
import javax.inject.Inject

class InsertUsedTodoUseCase @Inject constructor(private val repository: UsedTodoRepository) {
    suspend operator fun invoke(todo: Todo, routine: Routine) {
        repository.insert(todo, routine)
    }
}