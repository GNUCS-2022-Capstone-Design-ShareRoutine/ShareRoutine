package com.example.shareroutine.data.repository

import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.repository.RoutineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRoutineRepository(var routines: MutableList<Routine>? = mutableListOf()) : RoutineRepository {

    override suspend fun insert(routine: Routine) {
        routines?.add(routine)
    }

    override suspend fun deleteInLocal(routine: Routine) {
        routines?.remove(routine)
    }

    override suspend fun upload(routine: Routine) {
        routines?.add(routine)
    }

    override suspend fun deleteInRemote(routine: Routine) {
        routines?.remove(routine)
    }

    override fun getAllRoutinesFromLocal(): Flow<List<Routine>> {
        return flow {
            routines?.toList()?.let { emit(it) }
        }
    }

    override suspend fun fetchRoutine(id: String): Routine {
        return routines?.get(0)!!
    }
}