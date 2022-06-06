package com.example.shareroutine

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.shareroutine.data.repository.PostRepositoryImpl
import com.example.shareroutine.domain.model.Post
import com.example.shareroutine.domain.repository.PostRepository
import com.example.shareroutine.domain.usecase.post.GetPostListUseCase
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.ZonedDateTime

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class FirebaseRealtimeDatabasePostTest {
    private lateinit var db: FirebaseDatabase
    private lateinit var dataSource: PostDataSource
    private lateinit var repo: PostRepository
    private lateinit var getUseCase: GetPostListUseCase

    private val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        db = FirebaseDatabase.getInstance("http://10.0.2.2:9000/?ns=shareroutine")
        dataSource = PostDataSourceImplWithRealtime(db.getReference("community/post"))
        repo = PostRepositoryImpl(dataSource, testDispatcher)
        getUseCase = GetPostListUseCase(repo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun insert_test() = runTest {
        val post = Post(
            "Title 3", "",
            0, 0,
            "Description 3", ZonedDateTime.now()
        )

        val job = launch {
            withContext(Dispatchers.IO) {
                repo.insert(post)
            }
        }
        job.join()
    }

    @Test
    fun get_test() = runTest {
        val list = mutableListOf<Post>()

        val job = launch(Dispatchers.IO) {
            val myList = getUseCase().first()

            myList.forEach {
                list.add(it)
            }
        }

        job.join()

        println(list)

        assertThat(list.isEmpty(), `is`(false))
    }
}