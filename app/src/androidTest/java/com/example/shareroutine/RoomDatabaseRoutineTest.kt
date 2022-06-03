package com.example.shareroutine

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.shareroutine.data.source.room.AppDatabase
import com.example.shareroutine.data.source.room.dao.RoutineDao
import com.example.shareroutine.data.source.room.entity.RoomEntityRoutine
import com.example.shareroutine.data.source.room.entity.RoutineWithTodo
import com.example.shareroutine.data.source.room.entity.RoomEntityTodo
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import kotlin.Exception
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.Month
import java.util.concurrent.Executors

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class RoomDatabaseRoutineTest {
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

    @Test
    @Throws(Exception::class)
    fun getRoutinesWithTodos_shouldGetEmptyList() = runTest {
        val list = routineDao.getRoutinesWithTodos().first()

        assertThat(list.isEmpty(), `is`(true))
    }

    @Test
    @Throws(Exception::class)
    fun insert_thenCheckTotalSize_andCheckEachValue() = runTest {
        val routine = RoomEntityRoutine(name = "Routine1", term = 0, isUsed = false)

        val todo1 = RoomEntityTodo(
            time = LocalTime.parse("16:30"),
            importance = 1,
            description = "Todo1 description"
        )
        val todo2 = RoomEntityTodo(
            dayOfWeek = DayOfWeek.of(4),
            importance = 1,
            description = "Todo2 description"
        )
        val todo3 = RoomEntityTodo(
            month = Month.APRIL,
            importance = 3,
            description = "Todo3 description"
        )

        val routineWithTodo = RoutineWithTodo(routine, mutableListOf(todo1, todo2, todo3))
        routineDao.insert(routineWithTodo)

        val list = routineDao.getRoutinesWithTodos().first()
        assertThat(list.size, `is`(1))

        val foundRoutine = routineDao.getRoutineWithTodosByName(routine.name)!!
        assertThat(foundRoutine.roomEntityRoutine.name, `is`(routineWithTodo.roomEntityRoutine.name))
        assertThat(foundRoutine.roomEntityRoutine.term, `is`(routineWithTodo.roomEntityRoutine.term))
        assertThat(foundRoutine.roomEntityRoutine.isUsed, `is`(routineWithTodo.roomEntityRoutine.isUsed))
        assertThat(foundRoutine.roomEntityTodos.size, `is`(routineWithTodo.roomEntityTodos.size))
    }

    @Test
    @Throws(Exception::class)
    fun updateName_shouldNameUpdated() = runTest {
        val routine = RoomEntityRoutine(name = "Routine1", term = 0, isUsed = false)

        val todo1 = RoomEntityTodo(
            time = LocalTime.parse("16:30"),
            importance = 1,
            description = "Todo1 description"
        )
        val todo2 = RoomEntityTodo(
            dayOfWeek = DayOfWeek.of(4),
            importance = 1,
            description = "Todo2 description"
        )
        val todo3 = RoomEntityTodo(
            month = Month.APRIL,
            importance = 3,
            description = "Todo3 description"
        )

        val routineWithTodo = RoutineWithTodo(routine, mutableListOf(todo1, todo2, todo3))
        routineDao.insert(routineWithTodo)

        val before = routineDao.getRoutineWithTodosByName(routineWithTodo.roomEntityRoutine.name)!!
        assertThat(before, `is`(not(nullValue())))

        before.roomEntityRoutine.name = "Routine2"
        routineDao.update(before)

        val after = routineDao.getRoutineWithTodosByName(before.roomEntityRoutine.name)!!
        assertThat(after, `is`(not(nullValue())))
    }

    // Entity의 onUpdate를 이용할 방법 찾기
    @Test
    @Throws(Exception::class)
    fun updateTodoSize_thenCheckTodoSize() = runTest {
        val routine = RoomEntityRoutine(name = "Routine1", term = 0, isUsed = false)

        val todo1 = RoomEntityTodo(
            time = LocalTime.parse("16:30"),
            importance = 1,
            description = "Todo1 description"
        )
        val todo2 = RoomEntityTodo(
            dayOfWeek = DayOfWeek.of(4),
            importance = 1,
            description = "Todo2 description"
        )
        val todo3 = RoomEntityTodo(
            month = Month.APRIL,
            importance = 3,
            description = "Todo3 description"
        )

        val routineWithTodo = RoutineWithTodo(routine, mutableListOf(todo1, todo2))
        routineDao.insert(routineWithTodo)

        val before = routineDao.getRoutineWithTodosByName(routineWithTodo.roomEntityRoutine.name)!!
        assertThat(before.roomEntityTodos.size, `is`(2))

        before.roomEntityTodos.add(todo3)
        routineDao.update(before)

        val after = routineDao.getRoutineWithTodosByName(before.roomEntityRoutine.name)!!
        assertThat(after.roomEntityTodos.size, `is`(3))
    }

    @Test
    @Throws(Exception::class)
    fun deleteRoutine_thenShouldRoutineDeleted() = runTest {
        val routine = RoomEntityRoutine(name = "Routine1", term = 0, isUsed = false)

        val todo1 = RoomEntityTodo(
            time = LocalTime.parse("16:30"),
            importance = 1,
            description = "Todo1 description"
        )
        val todo2 = RoomEntityTodo(
            dayOfWeek = DayOfWeek.of(4),
            importance = 1,
            description = "Todo2 description"
        )
        val todo3 = RoomEntityTodo(
            month = Month.APRIL,
            importance = 3,
            description = "Todo3 description"
        )

        val routineWithTodo = RoutineWithTodo(routine, mutableListOf(todo1, todo2, todo3))
        routineDao.insert(routineWithTodo)

        val before = routineDao.getRoutineWithTodosByName(routineWithTodo.roomEntityRoutine.name)!!
        assertThat(before, `is`(not(nullValue())))

        println(before.toString())

        routineDao.delete(before.roomEntityRoutine)

        val after = routineDao.getTodosWithRoutineId(before.roomEntityRoutine.id!!).first()
        assertThat(after.size, `is`(0))
    }
}