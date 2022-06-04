package com.example.shareroutine.domain.repository

import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.model.Todo
import com.example.shareroutine.domain.model.UsedTodo
import kotlinx.coroutines.flow.Flow

interface UsedTodoRepository {
    suspend fun insert(todo: Todo, routine: Routine)
    suspend fun update(usedTodo: UsedTodo)
    suspend fun delete(usedTodo: UsedTodo)

    suspend fun getUsedTodoListByRoutine(routine: Routine): List<UsedTodo>
    fun getUsedTodoList(): Flow<List<UsedTodo>>
}