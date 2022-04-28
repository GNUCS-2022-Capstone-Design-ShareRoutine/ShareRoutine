package com.example.shareroutine.domain.repository

import com.example.shareroutine.domain.model.Routine
import kotlinx.coroutines.flow.Flow

interface RoutineRepository {
    suspend fun getAllRoutines(): Flow<List<Routine>>
}