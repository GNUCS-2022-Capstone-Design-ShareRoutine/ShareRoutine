package com.example.shareroutine.domain.usecase.routine

import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.repository.RoutineRepository
import javax.inject.Inject

class GetRoutineByNameUseCase @Inject constructor(private val repository: RoutineRepository) {
    suspend operator fun invoke(name: String): Routine? {
        return repository.getRoutineByName(name)
    }
}