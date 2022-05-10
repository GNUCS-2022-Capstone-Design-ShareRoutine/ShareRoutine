package com.example.shareroutine.domain.repository

import com.example.shareroutine.domain.model.Routine
import kotlinx.coroutines.flow.Flow

interface RoutineRepository {
    suspend fun insert(routine: Routine)
    suspend fun deleteInLocal(routine: Routine)

    suspend fun upload(routine: Routine)
    suspend fun deleteInRemote(routine: Routine)

    fun getAllRoutinesFromLocal(): Flow<List<Routine>>

    suspend fun fetchRoutine(): Routine
}