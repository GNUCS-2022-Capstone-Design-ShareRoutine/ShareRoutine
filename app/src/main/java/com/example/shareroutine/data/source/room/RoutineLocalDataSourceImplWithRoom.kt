package com.example.shareroutine.data.source.room

import com.example.shareroutine.data.source.RoutineLocalDataSource
import com.example.shareroutine.data.source.room.dao.RoutineDao
import com.example.shareroutine.data.source.room.entity.RoutineWithTodo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoutineLocalDataSourceImplWithRoom @Inject constructor(private val dao: RoutineDao) : RoutineLocalDataSource {

    override suspend fun insert(routine: RoutineWithTodo) {
        dao.insert(routine)
    }

    override suspend fun update(routine: RoutineWithTodo) {
        dao.update(routine)
    }

    override suspend fun delete(routine: RoutineWithTodo) {
        dao.delete(routine)
    }

    override fun getRoutineList(): Flow<List<RoutineWithTodo>> {
        return dao.getRoutinesWithTodos()
    }
}