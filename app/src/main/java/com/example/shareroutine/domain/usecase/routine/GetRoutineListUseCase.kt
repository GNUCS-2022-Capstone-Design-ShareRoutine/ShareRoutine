package com.example.shareroutine.domain.usecase.routine

import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.repository.RoutineRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRoutineListUseCase @Inject constructor(private val repository: RoutineRepository) {
    operator fun invoke(): Flow<List<Routine>> {
        return repository.getAllRoutinesFromLocal()
    }
}