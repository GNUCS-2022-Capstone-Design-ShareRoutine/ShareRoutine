package com.example.shareroutine.ui.community

import androidx.lifecycle.*
import com.example.shareroutine.di.IoDispatcher
import com.example.shareroutine.domain.model.Post
import com.example.shareroutine.domain.usecase.GetPostListUseCase
import com.example.shareroutine.domain.usecase.InsertPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    getPostListUseCase: GetPostListUseCase,
    private val insertPostUseCase: InsertPostUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _posts = getPostListUseCase().asLiveData()
    val posts: LiveData<List<Post>> get() = _posts

    fun insertNewPost(post: Post) {
        viewModelScope.launch(ioDispatcher) {
            insertPostUseCase(post)
        }
    }
}