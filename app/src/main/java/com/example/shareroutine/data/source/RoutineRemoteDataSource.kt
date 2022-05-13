package com.example.shareroutine.data.source

import com.example.shareroutine.data.source.realtime.State
import com.example.shareroutine.data.source.realtime.model.RealtimeDBModelRoutineWithTodo

interface RoutineRemoteDataSource {
    suspend fun insert(routineWithTodo: RealtimeDBModelRoutineWithTodo)
    suspend fun delete(routineWithTodo: RealtimeDBModelRoutineWithTodo)

    suspend fun fetchRoutine(id: String): State<RealtimeDBModelRoutineWithTodo>
}