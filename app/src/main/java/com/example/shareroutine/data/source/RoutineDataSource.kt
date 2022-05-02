package com.example.shareroutine.data.source

import androidx.lifecycle.LiveData
import com.example.shareroutine.data.source.room.dao.RoutineDao
import com.example.shareroutine.data.source.room.entity.RoutineWithTodo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

interface RoutineDataSource {
    suspend fun insert(routine: RoutineWithTodo)
    suspend fun update(routine: RoutineWithTodo)
    suspend fun delete(routine: RoutineWithTodo)

    fun getRoutineList(): Flow<List<RoutineWithTodo>>
    fun getUsedRoutineList(): Flow<List<RoutineWithTodo>>
}

class RoutineDataSourceImpl(private val dao: RoutineDao) : RoutineDataSource {
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