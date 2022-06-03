package com.example.shareroutine

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.shareroutine.data.source.RoutineRemoteDataSource
import com.example.shareroutine.data.source.realtime.State
import com.example.shareroutine.data.source.realtime.RoutineDataSourceImplWithRealtime
import com.example.shareroutine.data.source.realtime.model.RealtimeDBModelRoutine
import com.example.shareroutine.data.source.realtime.model.RealtimeDBModelRoutineWithTodo
import com.example.shareroutine.data.source.realtime.model.RealtimeDBModelTodo
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class FirebaseRealtimeDatabaseRoutineTest {
    private lateinit var db: FirebaseDatabase
    private lateinit var remote: RoutineRemoteDataSource

    private val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        db = FirebaseDatabase.getInstance("http://10.0.2.2:9000/?ns=shareroutine")
        remote = RoutineDataSourceImplWithRealtime(
            db.getReference("routines"),
            db.getReference("todos")
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun insert_test() = runTest {
        val todo1 = RealtimeDBModelTodo(time = "16:30", importance = 1, description = "Description 3")
        val todo2 = RealtimeDBModelTodo(dayOfWeek = 5, importance = 2, description = "Description 4")
        val routine = RealtimeDBModelRoutine(name = "루틴 2", term = 0, userId = "userId1")

        val routineWithTodo = RealtimeDBModelRoutineWithTodo(
            routine, mutableListOf(todo1, todo2)
        )

        launch(Dispatchers.IO) {
            remote.insert(routineWithTodo)
        }.join()
    }

    @Test
    fun delete_test() = runTest {
        var routine = RealtimeDBModelRoutineWithTodo()

        launch(Dispatchers.IO) {
            when (val result = remote.fetchRoutine("routineId1")) {
                is State.Success -> { routine = result.data }
                is State.Failed -> { println(result.message) }
            }
        }.join()

        launch(Dispatchers.IO) {
            remote.delete(routine)
        }.join()
    }

    @Test
    fun get_test() = runTest {
        var routine = RealtimeDBModelRoutineWithTodo()

        launch(Dispatchers.IO) {

            when (val result = remote.fetchRoutine("-N1xIJC4LulbIvLzGcfY")) {
                is State.Success -> { routine = result.data }
                is State.Failed -> { println(result.message) }
            }
        }.join()

        println("Value: ${routine.routine}")
        println("Value: ${routine.todos}")
    }
}