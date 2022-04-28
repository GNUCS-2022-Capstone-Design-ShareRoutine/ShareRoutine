package com.example.shareroutine.data.repository

import com.example.shareroutine.data.mapper.RoutineMapper
import com.example.shareroutine.data.source.RoutineDataSource
import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.repository.RoutineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class RoutineRepositoryImpl(private val dataSource: RoutineDataSource): RoutineRepository {
    override suspend fun getAllRoutines(): Flow<List<Routine>> = flow {
        dataSource.getRoutineList().collect { list ->
            emit(list.map { RoutineMapper.mapperToRoutine(it) })
        }
    }
}