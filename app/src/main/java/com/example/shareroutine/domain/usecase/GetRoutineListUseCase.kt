package com.example.shareroutine.domain.usecase

import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.repository.RoutineRepository
import kotlinx.coroutines.flow.Flow

class GetRoutineListUseCase(private val repository: RoutineRepository) {
    suspend fun invoke(): Flow<List<Routine>> {
        return repository.getAllRoutines()
    }
}