package com.example.shareroutine

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4

import com.example.shareroutine.data.source.room.AppDatabase
import com.example.shareroutine.data.source.room.dao.RoutineDao
import com.example.shareroutine.data.source.room.entity.Routine
import com.example.shareroutine.data.source.room.entity.RoutineWithTodo
import com.example.shareroutine.data.source.room.entity.Todo
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.lang.Exception
import java.time.ZonedDateTime
import java.util.concurrent.Executors

@RunWith(AndroidJUnit4::class)
class RoomDatabaseTest {
    private lateinit var db: AppDatabase
    private lateinit var routineDao: RoutineDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).setTransactionExecutor(Executors.newSingleThreadExecutor()).build()
        routineDao = db.routineDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @ExperimentalCoroutinesApi
    @Test
    @Throws(Exception::class)
    fun writeRoutineAndReadInList() = runTest {
        val routine = Routine(name = "Routine1", term = 0)

        val todo1 = Todo(dateTime = ZonedDateTime.now(), achieved = false, importance = 0, description = "Todo1 description")
        val todo2 = Todo(dateTime = ZonedDateTime.now(), achieved = false, importance = 1, description = "Todo1 description")
        val todo3 = Todo(dateTime = ZonedDateTime.now(), achieved = false, importance = 3, description = "Todo1 description")

        val routineWithTodo = RoutineWithTodo(routine, listOf(todo1, todo2, todo3))

        routineDao.insert(routineWithTodo)

        val before = routineDao.getRoutinesWithTodos().firstOrNull()
        before!!.map {
            Log.d("Before", it.toString())
        }

        val foundRoutine = before[0].routine
        val nextRoutineWithTodo = RoutineWithTodo(foundRoutine, listOf(todo1, todo2))

        routineDao.update(nextRoutineWithTodo)

        val after = routineDao.getRoutinesWithTodos().firstOrNull()
        after!!.map {
            Log.d("After", it.toString())
        }
    }
}