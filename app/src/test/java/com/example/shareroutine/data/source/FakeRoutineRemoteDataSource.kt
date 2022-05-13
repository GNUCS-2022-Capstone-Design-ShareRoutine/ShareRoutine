package com.example.shareroutine.data.source

import com.example.shareroutine.data.source.realtime.State
import com.example.shareroutine.data.source.realtime.model.RealtimeDBModelRoutineWithTodo

class FakeRoutineRemoteDataSource(var routines: MutableList<RealtimeDBModelRoutineWithTodo>? = mutableListOf()) : RoutineRemoteDataSource {
    override suspend fun insert(routineWithTodo: RealtimeDBModelRoutineWithTodo) {
        routines?.add(routineWithTodo)
    }

    override suspend fun delete(routineWithTodo: RealtimeDBModelRoutineWithTodo) {
        routines?.remove(routineWithTodo)
    }

    override suspend fun fetchRoutine(id: String): State<RealtimeDBModelRoutineWithTodo> {
        return State.success(routines?.get(0)!!)
    }
}