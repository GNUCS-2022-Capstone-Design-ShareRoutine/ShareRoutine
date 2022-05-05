package com.example.shareroutine.data.source

import com.example.shareroutine.data.source.room.entity.RoutineWithTodo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRoutineDataSource(var routines: MutableList<RoutineWithTodo>? = mutableListOf()) : RoutineDataSource {
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

    override fun getUsedRoutineList(): Flow<List<RoutineWithTodo>> {
        return flow {
            routines?.filter {
                it.roomEntityRoutine.isUsed
            }?.let { emit(it) }
        }
    }
}