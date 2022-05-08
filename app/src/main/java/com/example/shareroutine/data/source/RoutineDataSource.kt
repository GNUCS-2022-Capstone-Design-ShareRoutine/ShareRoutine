package com.example.shareroutine.data.source

import com.example.shareroutine.data.source.room.entity.RoutineWithTodo
import kotlinx.coroutines.flow.Flow

interface RoutineDataSource {
    suspend fun insert(routine: RoutineWithTodo)
    suspend fun update(routine: RoutineWithTodo)
    suspend fun delete(routine: RoutineWithTodo)

    fun getRoutineList(): Flow<List<RoutineWithTodo>>
}