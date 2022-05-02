package com.example.shareroutine.domain.repository

import com.example.shareroutine.domain.model.Routine
import kotlinx.coroutines.flow.Flow

interface RoutineRepository {
    suspend fun insert(routine: Routine)
    suspend fun delete(routine: Routine)

    fun getAllRoutines(): Flow<List<Routine>>
}