package com.example.shareroutine.domain.usecase.routine

import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.repository.RoutineRepository
import javax.inject.Inject

class DeleteRoutineInLocalUseCase @Inject constructor(
    private val repository: RoutineRepository
) {
    suspend operator fun invoke(routine: Routine) {
        repository.deleteInLocal(routine)
    }
}