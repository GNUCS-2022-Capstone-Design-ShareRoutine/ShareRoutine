package com.example.shareroutine.ui.community

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.shareroutine.data.repository.FakePostRepository
import com.example.shareroutine.domain.model.Post
import com.example.shareroutine.domain.repository.PostRepository
import com.example.shareroutine.domain.usecase.post.GetPostListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import java.time.ZonedDateTime

@ExperimentalCoroutinesApi
class CommunityViewModelTest {

    private val post1 = Post(
        "Title 1", "userId1", 0, 0, "Description 1", ZonedDateTime.now()
    )

    private lateinit var repo: PostRepository
    private lateinit var getUseCase: GetPostListUseCase
    private lateinit var viewModel: CommunityViewModel

    private val testCoroutineDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testCoroutineDispatcher)
        repo = FakePostRepository()
        getUseCase = GetPostListUseCase(repo)
        viewModel = CommunityViewModel(getUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}