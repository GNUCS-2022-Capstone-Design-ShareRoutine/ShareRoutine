package com.example.shareroutine.data.source.room

import com.example.shareroutine.data.source.RoutineDataSource
import com.example.shareroutine.data.source.room.dao.RoutineDao
import com.example.shareroutine.data.source.room.entity.RoutineWithTodo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoutineDataSourceImplWithRoom @Inject constructor(private val dao: RoutineDao) : RoutineDataSource {

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

    override fun getUsedRoutineList(): Flow<List<RoutineWithTodo>> {
        return dao.getUsedRoutinesWithTodos()
    }
}