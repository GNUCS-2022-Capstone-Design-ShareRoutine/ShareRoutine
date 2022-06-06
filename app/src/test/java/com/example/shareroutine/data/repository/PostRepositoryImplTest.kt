package com.example.shareroutine.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.shareroutine.data.mapper.PostMapper
import com.example.shareroutine.data.source.FakePostDataSource
import com.example.shareroutine.data.source.realtime.model.RealtimeDBModelPost
import com.example.shareroutine.domain.model.Post
import com.example.shareroutine.domain.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.ZonedDateTime

@ExperimentalCoroutinesApi
class PostRepositoryImplTest {
    private val post1 = RealtimeDBModelPost(
        "id1", "routineId1",
        "userId1", "Title 1",
        "Description 1", 0, 0,
        ZonedDateTime.now().toInstant().toEpochMilli()
    )
    private val post2 = RealtimeDBModelPost(
        "id2", "routineId2",
        "userId2", "Title 2",
        "Description 2", 0, 0,
        ZonedDateTime.now().toInstant().toEpochMilli()
    )

    private val remotePosts = mutableListOf(post1, post2)

    private lateinit var dataSource: PostDataSource
    private lateinit var repo: PostRepository

    private val dispatcher = UnconfinedTestDispatcher()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        dataSource = FakePostDataSource(remotePosts)
        repo = PostRepositoryImpl(dataSource, dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getAllPosts_fromRemoteDataSource() = runTest {
        val posts = repo.getAllPosts().first()

        val mapped = remotePosts.map {
            PostMapper.mapperToPost(it)
        }

        assertThat(posts, `is`(mapped))
    }

    @Test
    fun insert_thenCheckTotalSize_andCheckEachValue() = runTest {
        val newPost = Post("Title 3", "userId3", 0, 0, "Description 3", ZonedDateTime.now())

        repo.insert(newPost)

        val posts = repo.getAllPosts().first()

        assertThat(posts.size, `is`(3))
        assertThat(posts[2].title, `is`(newPost.title))
        assertThat(posts[2].userId, `is`(newPost.userId))
        assertThat(posts[2].liked, `is`(newPost.liked))
        assertThat(posts[2].downloaded, `is`(newPost.downloaded))
        assertThat(posts[2].dateTime, `is`(newPost.dateTime))
    }
}