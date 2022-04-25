package com.example.shareroutine.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.shareroutine.data.dao.TodoDao
import com.example.shareroutine.data.model.Routine
import com.example.shareroutine.data.model.Todo

class TodoRepository(private val todoDao: TodoDao) {
    val allRoutine: LiveData<List<Todo>> = todoDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(todo: Todo) {
        todoDao.insert(todo)
    }

}