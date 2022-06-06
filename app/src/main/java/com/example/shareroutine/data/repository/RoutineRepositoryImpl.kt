package com.example.shareroutine.data.repository

import com.example.shareroutine.data.mapper.RoutineMapper
import com.example.shareroutine.data.source.RoutineLocalDataSource
import com.example.shareroutine.data.source.RoutineRemoteDataSource
import com.example.shareroutine.data.source.realtime.State
import com.example.shareroutine.di.IoDispatcher
import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.repository.RoutineRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoutineRepositoryImpl @Inject constructor(
    private val localDataSource: RoutineLocalDataSource,
    private val remoteDataSource: RoutineRemoteDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
    ) : RoutineRepository {
    override suspend fun insert(routine: Routine) = withContext(ioDispatcher) {
        localDataSource.insert(RoutineMapper.fromRoutineToRoutineWithTodo(routine))
    }

    override suspend fun update(routine: Routine) {
        localDataSource.update(RoutineMapper.fromRoutineToRoutineWithTodo(routine))
    }

    override suspend fun deleteInLocal(routine: Routine) = withContext(ioDispatcher) {
        localDataSource.delete(RoutineMapper.fromRoutineToRoutineWithTodo(routine))
    }

    override suspend fun upload(routine: Routine) = withContext(ioDispatcher) {
        remoteDataSource.insert(RoutineMapper.fromRoutineToRealtimeDBModelRoutineWithTodo(routine))
    }

    override suspend fun deleteInRemote(routine: Routine) = withContext(ioDispatcher) {
        remoteDataSource.delete(RoutineMapper.fromRoutineToRealtimeDBModelRoutineWithTodo(routine))
    }

    override fun getAllRoutinesFromLocal(): Flow<List<Routine>> {
        return localDataSource.getRoutineList().map { list ->
            list.map {
                RoutineMapper.fromRoutineWithTodoToRoutine(it)
            }
        }
    }

    override suspend fun fetchRoutine(id: String): Routine = withContext(ioDispatcher) {
        return@withContext when (val routine = remoteDataSource.fetchRoutine(id)) {
            is State.Success -> {
                RoutineMapper.fromRealtimeDBModelRoutineWithTodoToRoutine(routine.data)
            }
            is State.Failed -> throw Exception(routine.message)
        }
    }
}