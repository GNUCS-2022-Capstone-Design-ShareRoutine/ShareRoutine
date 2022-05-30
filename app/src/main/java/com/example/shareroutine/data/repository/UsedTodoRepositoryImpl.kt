package com.example.shareroutine.data.repository

import com.example.shareroutine.data.mapper.UsedTodoMapper
import com.example.shareroutine.data.source.UsedTodoDataSource
import com.example.shareroutine.di.IoDispatcher
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
    override suspend fun insert(usedTodo: UsedTodo) = withContext(ioDispatcher) {
        dataSource.insert(UsedTodoMapper.fromUsedTodoToRoomEntityUsedTodo(usedTodo))
    }

    override suspend fun update(usedTodo: UsedTodo) = withContext(ioDispatcher) {
        dataSource.update(UsedTodoMapper.fromUsedTodoToRoomEntityUsedTodo(usedTodo))
    }

    override suspend fun delete(usedTodo: UsedTodo) = withContext(ioDispatcher) {
        dataSource.delete(UsedTodoMapper.fromUsedTodoToRoomEntityUsedTodo(usedTodo))
    }

    override fun getUsedTodoList(): Flow<List<UsedTodo>> {
        return dataSource.getUsedTodoList().flowOn(ioDispatcher).map { list ->
            list.map {
                UsedTodoMapper.fromRoomEntityUsedTodoToUsedTodo(it)
            }
        }
    }
}