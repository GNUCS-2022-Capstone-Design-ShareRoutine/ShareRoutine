package com.example.shareroutine.data.source.room

import com.example.shareroutine.data.source.UsedTodoDataSource
import com.example.shareroutine.data.source.room.dao.UsedTodoDao
import com.example.shareroutine.data.source.room.entity.RoomEntityUsedTodo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsedTodoDataSourceImplWithRoom @Inject constructor(private val dao: UsedTodoDao) : UsedTodoDataSource {
    override suspend fun insert(todo: RoomEntityUsedTodo) {
        dao.insert(todo)
    }

    override suspend fun update(todo: RoomEntityUsedTodo) {
        dao.update(todo)
    }

    override suspend fun delete(todo: RoomEntityUsedTodo) {
        dao.delete(todo)
    }

    override suspend fun getUsedTodoListByRoutineId(routineId: Int): List<RoomEntityUsedTodo> {
        return dao.getUsedTodosByRoutineId(routineId)
    }

    override fun getUsedTodoList(): Flow<List<RoomEntityUsedTodo>> {
        return dao.getUsedTodos()
    }
}