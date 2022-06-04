package com.example.shareroutine.data.repository

import com.example.shareroutine.data.mapper.UsedTodoMapper
import com.example.shareroutine.data.source.UsedTodoDataSource
import com.example.shareroutine.di.IoDispatcher
import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.model.Todo
import com.example.shareroutine.domain.model.UsedTodo
import com.example.shareroutine.domain.repository.UsedTodoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsedTodoRepositoryImpl @Inject constructor(
    private val dataSource: UsedTodoDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
    ) : UsedTodoRepository {
    override suspend fun insert(todo: Todo, routine: Routine) = withContext(ioDispatcher) {
        val usedTodo = UsedTodoMapper.fromTodoToUsedTodo(todo, routine)!!

        dataSource.insert(
            UsedTodoMapper.fromUsedTodoToRoomEntityUsedTodo(usedTodo)
        )
    }

    override suspend fun update(usedTodo: UsedTodo) = withContext(ioDispatcher) {
        dataSource.update(UsedTodoMapper.fromUsedTodoToRoomEntityUsedTodo(usedTodo))
    }

    override suspend fun delete(usedTodo: UsedTodo) = withContext(ioDispatcher) {
        dataSource.delete(UsedTodoMapper.fromUsedTodoToRoomEntityUsedTodo(usedTodo))
    }

    override suspend fun getUsedTodoListByRoutine(routine: Routine): List<UsedTodo> = withContext(ioDispatcher) {
        val usedTodos = dataSource.getUsedTodoListByRoutineId(routine.id!!)

        return@withContext usedTodos.map { UsedTodoMapper.fromRoomEntityUsedTodoToUsedTodo(it) }
    }

    override fun getUsedTodoList(): Flow<List<UsedTodo>> {
        return dataSource.getUsedTodoList().flowOn(ioDispatcher).map { list ->
            list.map {
                UsedTodoMapper.fromRoomEntityUsedTodoToUsedTodo(it)
            }
        }
    }
}