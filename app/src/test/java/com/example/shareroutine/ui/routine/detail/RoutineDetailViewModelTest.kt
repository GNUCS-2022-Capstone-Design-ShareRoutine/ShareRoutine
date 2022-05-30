package com.example.shareroutine.ui.routine.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.shareroutine.data.repository.FakeRoutineRepository
import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.model.Term
import com.example.shareroutine.domain.model.Todo
import com.example.shareroutine.domain.repository.RoutineRepository
import com.example.shareroutine.domain.usecase.GetUsedRoutineListUseCase
import com.example.shareroutine.domain.usecase.routine.InsertRoutineUseCase
import com.example.shareroutine.getOrAwaitValue
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
import java.time.ZonedDateTime

@ExperimentalCoroutinesApi
class RoutineDetailViewModelTest {

    private val todo1 = Todo(ZonedDateTime.now(), 1, "Description 1", false)
    private val todo2 = Todo(ZonedDateTime.now(), 1, "Description 1", false)
    private val routine = Routine("Routine 1", Term.DAILY, true, listOf(todo1, todo2))

    private lateinit var repo: RoutineRepository
    private lateinit var getUseCase: GetUsedRoutineListUseCase
    private lateinit var insertUseCase: InsertRoutineUseCase
    private lateinit var viewModel: RoutineDetailViewModel

    private val testCoroutineDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testCoroutineDispatcher)
        repo = FakeRoutineRepository()
        getUseCase = GetUsedRoutineListUseCase(repo)
        insertUseCase = InsertRoutineUseCase(repo)
        viewModel = RoutineDetailViewModel(getUseCase, insertUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun insertRoutine_ThenCheckUsedRoutineSize() {
        viewModel.insertNewRoutine(routine)
        val after = viewModel.usedRoutines.getOrAwaitValue()

        assertThat(after.size, `is`(not(0)))
    }
}