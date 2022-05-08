package com.example.shareroutine.domain.usecase

import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.repository.RoutineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUsedRoutineListUseCase @Inject constructor(private val repository: RoutineRepository) {
    operator fun invoke(): Flow<List<Routine>> {
        return repository.getAllRoutines().map { it.filter { item -> item.isUsed } }
    }
}