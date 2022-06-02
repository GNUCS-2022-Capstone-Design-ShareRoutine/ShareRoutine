package com.example.shareroutine.ui.routine.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.shareroutine.data.repository.FakeRoutineRepository
import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.model.Term
import com.example.shareroutine.domain.model.Todo
import com.example.shareroutine.domain.repository.RoutineRepository
import com.example.shareroutine.domain.usecase.routine.GetUsedTodoListUseCase
import com.example.shareroutine.domain.usecase.routine.InsertRoutineUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalTime

@ExperimentalCoroutinesApi
class RoutineDetailViewModelTest {

    private val todo1 = Todo(
        time = LocalTime.parse("16:30"),
        importance = 1,
        description = "Description 1"
    )
    private val todo2 = Todo(
        time = LocalTime.parse("09:30"),
        importance = 1,
        description = "Description 1"
    )
    private val routine = Routine(
        userId = "userId1",
        name = "Routine 1",
        term = Term.DAILY,
        isUsed = true,
        todos = listOf(todo1, todo2)
    )

    private lateinit var repo: RoutineRepository
    private lateinit var getUsedTodoCase: GetUsedTodoListUseCase
    private lateinit var insertUseCase: InsertRoutineUseCase
    private lateinit var viewModel: RoutineDetailViewModel

    private val testCoroutineDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testCoroutineDispatcher)
        repo = FakeRoutineRepository()
        // getUsedTodoCase = GetUsedTodoListUseCase(repo)
        insertUseCase = InsertRoutineUseCase(repo)
        viewModel = RoutineDetailViewModel(getUsedTodoCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}