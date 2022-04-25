package com.example.shareroutine.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.shareroutine.data.model.Routine
import com.example.shareroutine.data.dao.RoutineDao
import com.example.shareroutine.data.model.Todo
import com.example.shareroutine.data.dao.TodoDao

class RoutineRepository(private val routineDao: RoutineDao) {

    val allRoutine: LiveData<List<Routine>> = routineDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(routine: Routine) {
        routineDao.insert(routine)
    }
}