package com.example.shareroutine.data.source.room.dao

import androidx.room.*
import com.example.shareroutine.data.source.room.entity.RoomEntityRoutine
import com.example.shareroutine.data.source.room.entity.RoutineWithTodo
import com.example.shareroutine.data.source.room.entity.RoomEntityTodo
import kotlinx.coroutines.flow.*

@Dao
interface RoutineDao {
    @Insert
    suspend fun insert(roomEntityRoutine: RoomEntityRoutine): Long

    @Update
    suspend fun update(roomEntityRoutine: RoomEntityRoutine): Int

    @Delete
    suspend fun delete(roomEntityRoutine: RoomEntityRoutine)

    @Insert
    suspend fun insert(roomEntityTodo: RoomEntityTodo)

    @Update
    suspend fun update(roomEntityTodo: RoomEntityTodo)

    @Delete
    suspend fun delete(roomEntityTodo: RoomEntityTodo)

    @Query("SELECT * FROM todo_table WHERE routineId = :routineId")
    fun getTodosWithRoutineId(routineId: Int): Flow<List<RoomEntityTodo>>

    @Transaction
    suspend fun insert(routineWithTodo: RoutineWithTodo) {
        val routineId = insert(routineWithTodo.roomEntityRoutine)

        routineWithTodo.roomEntityTodos.map {
            it.routineId = routineId.toInt()
            insert(it)
        }
    }

    @Transaction
    suspend fun update(routineWithTodo: RoutineWithTodo) {
        val routineId = update(routineWithTodo.roomEntityRoutine)

        val previous = getTodosWithRoutineId(routineId).first()

        previous.map {
            delete(it)
        }

        routineWithTodo.roomEntityTodos.map {
            it.routineId = routineId
            insert(it)
        }
    }

    // 차후 제거
    @Transaction
    suspend fun delete(routineWithTodo: RoutineWithTodo) {
        delete(routineWithTodo.roomEntityRoutine)

        routineWithTodo.roomEntityTodos.map {
            delete(it)
        }
    }

    @Transaction
    @Query("SELECT * FROM routine_table WHERE name = :name")
    fun getRoutineWithTodosByName(name: String): Flow<RoutineWithTodo>

    @Transaction
    @Query("SELECT * FROM routine_table")
    fun getRoutinesWithTodos(): Flow<List<RoutineWithTodo>>

    @Transaction
    @Query("SELECT * FROM routine_table WHERE isUsed = 1")
    fun getUsedRoutinesWithTodos(): Flow<List<RoutineWithTodo>>
}