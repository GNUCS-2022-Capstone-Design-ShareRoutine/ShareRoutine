package com.example.shareroutine.ui.community

import androidx.lifecycle.*
import com.example.shareroutine.domain.model.Post
import com.example.shareroutine.domain.usecase.GetPostListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    getPostListUseCase: GetPostListUseCase,
) : ViewModel() {
    private val _posts = getPostListUseCase().asLiveData()
    val posts: LiveData<List<Post>> get() = _posts
}