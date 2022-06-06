package com.example.shareroutine.data.source.room.dao

import androidx.room.*
import com.example.shareroutine.data.source.room.entity.RoomEntityUsedTodo
import kotlinx.coroutines.flow.Flow

@Dao
interface UsedTodoDao {
    @Insert
    suspend fun insert(roomEntityUsedTodo: RoomEntityUsedTodo)

    @Update
    suspend fun update(roomEntityUsedTodo: RoomEntityUsedTodo)

    @Delete
    suspend fun delete(roomEntityUsedTodo: RoomEntityUsedTodo)

    @Query("SELECT * FROM used_todo_table WHERE routineId = :routineId")
    suspend fun getUsedTodosByRoutineId(routineId: Int): List<RoomEntityUsedTodo>

    @Query("SELECT * FROM used_todo_table")
    fun getUsedTodos(): Flow<List<RoomEntityUsedTodo>>
}