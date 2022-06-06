package com.example.shareroutine.ui.community

import androidx.lifecycle.*
import com.example.shareroutine.domain.model.Post
import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.usecase.post.InsertPostUseCase
import com.example.shareroutine.domain.usecase.post.UpdatePostUseCase
import com.example.shareroutine.domain.usecase.routine.GetRoutineListUseCase
import com.example.shareroutine.domain.usecase.user.FetchUserUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class CommunityAddViewModel @Inject constructor(
    getRoutineListUseCase: GetRoutineListUseCase,
    private val fetchUserUseCase: FetchUserUseCase,
    private val insertPostUseCase: InsertPostUseCase,
    private val updatePostUseCase: UpdatePostUseCase
) : ViewModel() {
    val routineList = getRoutineListUseCase().asLiveData()
    var selectedRoutine: Routine? = null

    private var currentPost: Post? = null

    fun writePost(title: String, hashTag: String, content: String): LiveData<Boolean> {
        val isSuccessful = MutableLiveData<Boolean>()

        viewModelScope.launch {
            val currentUser = FirebaseAuth.getInstance().currentUser!!

            val user = fetchUserUseCase(currentUser.uid)!!

            val hashTags: MutableList<String> = hashTag.split("#").toMutableList()
            hashTags.removeFirst()

            if (currentPost == null) {
                currentPost = Post(
                    title = title, user = user, liked = mutableListOf(),
                    downloaded = mutableListOf(), description = content,
                    dateTime = ZonedDateTime.now(), routine = selectedRoutine!!,
                    hashTags = hashTags
                )

                insertPostUseCase(currentPost!!)
            }
            else {
                currentPost!!.title = title
                currentPost!!.description = content
                currentPost!!.dateTime = ZonedDateTime.now()
                currentPost!!.routine = selectedRoutine!!
                currentPost!!.hashTags = hashTags

                updatePostUseCase(currentPost!!)
            }

            isSuccessful.value = true
        }

        return isSuccessful
    }

    fun setPost(post: Post) {
        currentPost = post
    }
}