package com.example.shareroutine.data.repository

import com.example.shareroutine.data.mapper.RoutineMapper
import com.example.shareroutine.data.source.FakeRoutineLocalDataSource
import com.example.shareroutine.data.source.room.entity.RoomEntityRoutine
import com.example.shareroutine.data.source.room.entity.RoomEntityTodo
import com.example.shareroutine.data.source.room.entity.RoutineWithTodo
import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.model.Term
import com.example.shareroutine.domain.model.Todo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import java.time.ZonedDateTime

// Test 빌드 깨짐
// TODO("FakeRoutineRemoteDataSource 작성 필요")

@ExperimentalCoroutinesApi
class RoutineRepositoryImplTest {
    private val routine = RoomEntityRoutine(
        id = 1,
        name = "Routine 1",
        term = 0,
        isUsed = false
    )
    private val todo1 = RoomEntityTodo(
        dateTime = ZonedDateTime.now(),
        importance = 1,
        description = "Description 1",
        achieved = false,
        routineId = routine.id
    )
    private val todo2 = RoomEntityTodo(
        dateTime = ZonedDateTime.now(),
        importance = 2,
        description = "Description 2",
        achieved = false,
        routineId = routine.id
    )
    private val todo3 = RoomEntityTodo(
        dateTime = ZonedDateTime.now(),
        importance = 3,
        description = "Description 3",
        achieved = false,
        routineId = routine.id
    )
    private val todos = mutableListOf(todo1, todo2, todo3)
    private val routineWithTodo = RoutineWithTodo(routine, todos)
    private val localRoutines = mutableListOf(routineWithTodo)

    private lateinit var dataSource: FakeRoutineLocalDataSource
    private lateinit var repository: RoutineRepositoryImpl

    @Before
    fun setUpRepo() {
        dataSource = FakeRoutineLocalDataSource(localRoutines)
        repository = RoutineRepositoryImpl(dataSource)
    }

    @Test
    fun getAllRoutines_fromLocalDataSource() = runTest {
        val routines = repository.getAllRoutines().first()

        val mapped = localRoutines.map {
            RoutineMapper.mapperToRoutine(it)
        }

        assertThat(routines, `is`(mapped))
    }

    @Test
    fun insert_thenCheckTotalSize_andCheckEachValue() = runTest {
        val domainTodo1 = Todo(ZonedDateTime.now(), 4, "Description 1", false)
        val domainTodo2 = Todo(ZonedDateTime.now(), 5, "Description 2", false)
        val todos = listOf(domainTodo1, domainTodo2)
        val routine = Routine("Routine 2", Term.WEEKLY, false, todos)

        repository.insert(routine)

        val routines = repository.getAllRoutines().first()

        assertThat(routines.size, `is`(2))
        assertThat(routines[1].name, `is`(routine.name))
        assertThat(routines[1].isUsed, `is`(routine.isUsed))
        assertThat(routines[1].term, `is`(routine.term))
        assertThat(routines[1].todos, `is`(todos))
    }
}