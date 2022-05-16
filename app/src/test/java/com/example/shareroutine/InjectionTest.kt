package com.example.shareroutine

import com.example.shareroutine.data.source.RoutineLocalDataSource
import com.example.shareroutine.data.source.room.dao.RoutineDao
import com.example.shareroutine.domain.repository.RoutineRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class)
class InjectionTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var routineDao: RoutineDao

    @Inject
    lateinit var localDataSource: RoutineLocalDataSource

    @Inject
    lateinit var repo: RoutineRepository

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun daoInjectionTest() {
        assertThat(routineDao, `is`(not(nullValue())))
    }

    @Test
    fun dataSourceInjectionTest() {
        assertThat(localDataSource, `is`(not(nullValue())))
    }

    @Test
    fun repoInjectionTest() {
        assertThat(repo, `is`(not(nullValue())))
    }
}