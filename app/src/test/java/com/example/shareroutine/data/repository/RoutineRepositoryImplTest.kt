package com.example.shareroutine.data.repository

import com.example.shareroutine.data.mapper.RoutineMapper
import com.example.shareroutine.data.source.FakeRoutineLocalDataSource
import com.example.shareroutine.data.source.FakeRoutineRemoteDataSource
import com.example.shareroutine.data.source.realtime.model.RealtimeDBModelRoutine
import com.example.shareroutine.data.source.realtime.model.RealtimeDBModelRoutineWithTodo
import com.example.shareroutine.data.source.realtime.model.RealtimeDBModelTodo
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

@ExperimentalCoroutinesApi
class RoutineRepositoryImplTest {
    private val roomRoutine = RoomEntityRoutine(
        id = 1,
        name = "Routine 1",
        term = 0,
        isUsed = false
    )
    private val roomTodo1 = RoomEntityTodo(
        dateTime = ZonedDateTime.now(),
        importance = 1,
        description = "Description 1",
        achieved = false,
        routineId = roomRoutine.id
    )
    private val roomTodo2 = RoomEntityTodo(
        dateTime = ZonedDateTime.now(),
        importance = 2,
        description = "Description 2",
        achieved = false,
        routineId = roomRoutine.id
    )
    private val roomTodo3 = RoomEntityTodo(
        dateTime = ZonedDateTime.now(),
        importance = 3,
        description = "Description 3",
        achieved = false,
        routineId = roomRoutine.id
    )
    private val roomTodos = mutableListOf(roomTodo1, roomTodo2, roomTodo3)
    private val routineWithTodo = RoutineWithTodo(roomRoutine, roomTodos)
    private val localRoutines = mutableListOf(routineWithTodo)

    private val realtimeRoutine = RealtimeDBModelRoutine(
        id = "routine2",
        name = "Routine 2",
        term = 0
    )

    private val realtimeTodo1 = RealtimeDBModelTodo("Description 1", 1)
    private val realtimeTodo2 = RealtimeDBModelTodo("Description 2", 2)
    private val realtimeRoutineWithTodo = RealtimeDBModelRoutineWithTodo(realtimeRoutine, mutableListOf(realtimeTodo1, realtimeTodo2))
    private val remoteRoutines = mutableListOf(realtimeRoutineWithTodo)

    private lateinit var local: FakeRoutineLocalDataSource
    private lateinit var remote: FakeRoutineRemoteDataSource
    private lateinit var repository: RoutineRepositoryImpl

    @Before
    fun setUpRepo() {
        local = FakeRoutineLocalDataSource(localRoutines)
        remote = FakeRoutineRemoteDataSource(remoteRoutines)
        repository = RoutineRepositoryImpl(local, remote)
    }

    @Test
    fun getAllRoutines_fromLocalDataSource() = runTest {
        val routines = repository.getAllRoutinesFromLocal().first()

        val mapped = localRoutines.map {
            RoutineMapper.fromRoutineWithTodoToRoutine(it)
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

        val routines = repository.getAllRoutinesFromLocal().first()

        assertThat(routines.size, `is`(2))
        assertThat(routines[1].name, `is`(routine.name))
        assertThat(routines[1].isUsed, `is`(routine.isUsed))
        assertThat(routines[1].term, `is`(routine.term))
        assertThat(routines[1].todos, `is`(todos))
    }
}