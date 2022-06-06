package com.example.shareroutine.ui.community

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shareroutine.domain.model.Post
import com.example.shareroutine.domain.usecase.post.DeletePostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val deletePostUseCase: DeletePostUseCase
) : ViewModel() {
    var currentPost: Post? = null

    fun setPost(post: Post) {
        currentPost = post
    }

    fun deleteCurrentPost(): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        viewModelScope.launch {
            deletePostUseCase(currentPost!!)

            result.value = true
        }

        return result
    }
}