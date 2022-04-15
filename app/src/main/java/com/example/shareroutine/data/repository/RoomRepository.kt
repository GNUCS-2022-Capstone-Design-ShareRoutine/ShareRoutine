package com.example.shareroutine.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.shareroutine.data.model.Routine
import com.example.shareroutine.data.model.RoutineDao
import com.example.shareroutine.data.model.Todo
import com.example.shareroutine.data.model.TodoDao
import java.util.concurrent.Flow

class RoomRepository(private val routineDao: RoutineDao, private val todoDao: TodoDao) {

    val allRoutine: LiveData<List<Routine>> = routineDao.getAll()
    val allTodo: LiveData<List<Todo>> = todoDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(routine: Routine, todo: Todo) {
        routineDao.insert(routine)
        todoDao.insert(todo)
    }
}