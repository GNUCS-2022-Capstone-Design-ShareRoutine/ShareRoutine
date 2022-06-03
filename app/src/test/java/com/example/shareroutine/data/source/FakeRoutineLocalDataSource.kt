package com.example.shareroutine.data.source

import com.example.shareroutine.data.source.room.entity.RoutineWithTodo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRoutineLocalDataSource(var routines: MutableList<RoutineWithTodo>? = mutableListOf()) : RoutineLocalDataSource {
    override suspend fun insert(routine: RoutineWithTodo) {
        routines?.add(routine)
    }

    override suspend fun update(routine: RoutineWithTodo) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(routine: RoutineWithTodo) {
        routines?.remove(routine)
    }

    override fun getRoutineList(): Flow<List<RoutineWithTodo>> {
        return flow {
            routines?.toList()?.let { emit(it) }
        }
    }

    override suspend fun getRoutineByName(name: String): RoutineWithTodo? {
        return routines?.find {
            it.roomEntityRoutine.name == name
        }
    }
}