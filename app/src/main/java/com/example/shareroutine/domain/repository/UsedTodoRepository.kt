package com.example.shareroutine.domain.repository

import com.example.shareroutine.domain.model.UsedTodo
import kotlinx.coroutines.flow.Flow

interface UsedTodoRepository {
    suspend fun insert(usedTodo: UsedTodo)
    suspend fun update(usedTodo: UsedTodo)
    suspend fun delete(usedTodo: UsedTodo)

    fun getUsedTodoList(): Flow<List<UsedTodo>>
}