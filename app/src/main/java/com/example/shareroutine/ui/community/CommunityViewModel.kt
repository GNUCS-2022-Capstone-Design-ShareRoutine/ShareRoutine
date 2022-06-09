package com.example.shareroutine.ui.community

import androidx.lifecycle.*
import com.example.shareroutine.domain.model.Post
import com.example.shareroutine.domain.usecase.post.GetPostListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    getPostListUseCase: GetPostListUseCase,
) : ViewModel() {
    // false = newest, true = liked
    val sorting = MutableLiveData(false)
    val posts = getPostListUseCase().asLiveData()
}