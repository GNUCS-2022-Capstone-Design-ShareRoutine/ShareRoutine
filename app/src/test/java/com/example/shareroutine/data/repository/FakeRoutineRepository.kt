package com.example.shareroutine.data.repository

import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.repository.RoutineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRoutineRepository(var routines: MutableList<Routine>? = mutableListOf()) : RoutineRepository {

    override suspend fun insert(routine: Routine) {
        routines?.add(routine)
    }

    override suspend fun delete(routine: Routine) {
        routines?.remove(routine)
    }

    override fun getAllRoutines(): Flow<List<Routine>> {
        return flow {
            routines?.toList()?.let { emit(it) }
        }
    }
}