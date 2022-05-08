package com.example.shareroutine.data.repository

import com.example.shareroutine.data.mapper.RoutineMapper
import com.example.shareroutine.data.source.RoutineDataSource
import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.repository.RoutineRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class RoutineRepositoryImpl @Inject constructor(private val dataSource: RoutineDataSource): RoutineRepository {
    override suspend fun insert(routine: Routine) {
        dataSource.insert(RoutineMapper.mapperToRoutineWithTodo(routine))
    }

    override suspend fun delete(routine: Routine) {
        dataSource.delete(RoutineMapper.mapperToRoutineWithTodo(routine))
    }

    override fun getAllRoutines(): Flow<List<Routine>> {
        return dataSource.getRoutineList().map { list ->
            list.map {
                RoutineMapper.mapperToRoutine(it)
            }
        }
    }
}