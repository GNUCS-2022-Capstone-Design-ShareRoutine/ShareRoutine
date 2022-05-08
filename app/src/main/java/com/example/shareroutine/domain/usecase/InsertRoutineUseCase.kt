package com.example.shareroutine.domain.usecase

import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.repository.RoutineRepository
import javax.inject.Inject

class InsertRoutineUseCase @Inject constructor(private val repository: RoutineRepository) {
    suspend operator fun invoke(routine: Routine) {
        repository.insert(routine)
    }
}