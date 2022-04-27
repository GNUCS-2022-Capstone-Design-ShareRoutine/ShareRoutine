package com.example.shareroutine

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.room.withTransaction
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4

import com.example.shareroutine.data.source.room.AppDatabase
import com.example.shareroutine.data.source.room.dao.RoutineDao
import com.example.shareroutine.data.source.room.entity.Routine
import com.example.shareroutine.data.source.room.entity.RoutineWithTodo
import com.example.shareroutine.data.source.room.entity.Todo
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import java.io.IOException
import java.lang.Exception
import java.time.ZonedDateTime
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class RoomDatabaseTest {
    private lateinit var db: AppDatabase
    private lateinit var routineDao: RoutineDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        routineDao = db.routineDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeRoutineAndReadInList(): Unit = runBlocking {
        val routine = Routine(name = "Routine1", term = 0)

        val todo1 = Todo(dateTime = ZonedDateTime.now(), achieved = false, importance = 0, description = "Todo1 description")
        val todo2 = Todo(dateTime = ZonedDateTime.now(), achieved = false, importance = 1, description = "Todo1 description")
        val todo3 = Todo(dateTime = ZonedDateTime.now(), achieved = false, importance = 3, description = "Todo1 description")
        val todo4 = Todo(dateTime = ZonedDateTime.now(), achieved = false, importance = 2, description = "Todo1 description")
        val todo5 = Todo(dateTime = ZonedDateTime.now(), achieved = false, importance = 2, description = "Todo1 description")

        val todoList = listOf(todo1, todo2, todo3, todo4, todo5)

        val routineWithTodo = RoutineWithTodo(routine, todoList)

        routineDao.insert(routineWithTodo)

        val stored = routineDao.getRoutinesWithTodos().blockingObserve()

        stored!!.map {
            Log.d("Room test", it.toString())
        }
    }

    private fun <T> LiveData<T>.blockingObserve(): T? {
        var value: T? = null
        val latch = CountDownLatch(1)

        val observer = Observer<T> { t ->
            value = t
            latch.countDown()
        }

        observeForever(observer)

        latch.await(2, TimeUnit.SECONDS)
        return value
    }
}