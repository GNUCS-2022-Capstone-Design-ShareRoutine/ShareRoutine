package com.example.shareroutine.ui.community

import androidx.lifecycle.*
import com.example.shareroutine.domain.model.Post
import com.example.shareroutine.domain.usecase.post.GetPostByIdUseCase
import com.example.shareroutine.domain.usecase.post.DeletePostUseCase
import com.example.shareroutine.domain.usecase.post.UpdatePostUseCase
import com.example.shareroutine.domain.usecase.routine.InsertRoutineUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getPostByIdUseCase: GetPostByIdUseCase,
    private val updatePostUseCase: UpdatePostUseCase,
    private val deletePostUseCase: DeletePostUseCase,
    private val insertRoutineUseCase: InsertRoutineUseCase
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

    fun observePost(id: String): LiveData<Post> {
        return getPostByIdUseCase(id).asLiveData()
    }

    fun likePost(): LiveData<Boolean> {
        val currentUser = FirebaseAuth.getInstance().currentUser!!

        if (currentPost!!.liked.none { it == currentUser.uid }) {
            currentPost!!.liked.add(currentUser.uid)
        }
        else {
            currentPost!!.liked.remove(currentUser.uid)
        }

        val result = MutableLiveData<Boolean>()

        viewModelScope.launch {
            updatePostUseCase(currentPost!!)

            result.value = true
        }

        return result
    }

    fun downloadRoutine(): LiveData<Boolean> {
        val currentUser = FirebaseAuth.getInstance().currentUser!!

        currentPost!!.downloaded.add(currentUser.uid)

        val result = MutableLiveData<Boolean>()

        viewModelScope.launch {
            insertRoutineUseCase(currentPost!!.routine)

            result.value = true
        }

        return result
    }
}