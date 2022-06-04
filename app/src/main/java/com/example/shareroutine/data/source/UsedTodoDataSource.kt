package com.example.shareroutine.data.source

import com.example.shareroutine.data.source.room.entity.RoomEntityUsedTodo
import kotlinx.coroutines.flow.Flow

interface UsedTodoDataSource {
    suspend fun insert(todo: RoomEntityUsedTodo)
    suspend fun update(todo: RoomEntityUsedTodo)
    suspend fun delete(todo: RoomEntityUsedTodo)

    suspend fun getUsedTodoListByRoutineId(routineId: Int): List<RoomEntityUsedTodo>
    fun getUsedTodoList(): Flow<List<RoomEntityUsedTodo>>
}