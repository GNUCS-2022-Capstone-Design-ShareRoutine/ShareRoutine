package com.example.shareroutine.data.source.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.shareroutine.data.source.room.entity.Routine
import com.example.shareroutine.data.source.room.entity.RoutineWithTodo
import com.example.shareroutine.data.source.room.entity.Todo


@Dao
interface RoutineDao {
    @Insert
    suspend fun insert(routine: Routine): Long

    @Update
    suspend fun update(routine: Routine): Int

    @Delete
    suspend fun delete(routine: Routine)

    @Query("SELECT * FROM routine_table")
    fun getAllRoutines(): LiveData<List<Routine>>

    @Insert
    suspend fun insert(todo: Todo)

    @Update
    suspend fun update(todo: Todo)

    @Delete
    suspend fun delete(todo: Todo)

    @Query("SELECT * FROM todo_table")
    fun getAllTodos(): LiveData<List<Todo>>

    @Query("SELECT * FROM todo_table WHERE routineId = :routineId")
    fun getTodosWithRoutineId(routineId: Int): LiveData<List<Todo>>

    @Transaction
    suspend fun insert(routineWithTodo: RoutineWithTodo) {
        val routineId = insert(routineWithTodo.routine)

        routineWithTodo.todos.map {
            it.routineId = routineId.toInt()
            insert(it)
        }
    }

    @Transaction
    suspend fun update(routineWithTodo: RoutineWithTodo) {
        val routineId = update(routineWithTodo.routine)

        val previous = getTodosWithRoutineId(routineId)

        previous.value!!.map {
            delete(it)
        }

        routineWithTodo.todos.map {
            it.routineId = routineId
            insert(it)
        }
    }

    @Transaction
    suspend fun delete(routineWithTodo: RoutineWithTodo) {
        delete(routineWithTodo.routine)

        routineWithTodo.todos.map {
            delete(it)
        }
    }

    @Transaction
    @Query("SELECT * FROM routine_table")
    fun getRoutinesWithTodos(): LiveData<List<RoutineWithTodo>>
}