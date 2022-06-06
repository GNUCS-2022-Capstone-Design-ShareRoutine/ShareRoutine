package com.example.shareroutine.ui.community

import androidx.lifecycle.*
import com.example.shareroutine.domain.model.Post
import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.usecase.InsertPostUseCase
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
    private val insertPostUseCase: InsertPostUseCase
) : ViewModel() {
    val routineList = getRoutineListUseCase().asLiveData()
    var selectedRoutine: Routine? = null

    fun writePost(title: String, hashTag: String, content: String): LiveData<Boolean> {
        val isSuccessful = MutableLiveData<Boolean>()

        viewModelScope.launch {
            val currentUser = FirebaseAuth.getInstance().currentUser!!

            val user = fetchUserUseCase(currentUser.uid)!!

            val post = Post(
                title = title, user = user,
                liked = 0, downloaded = 0, description = content,
                dateTime = ZonedDateTime.now(), routine = selectedRoutine!!
            )

            insertPostUseCase(post)
            isSuccessful.value = true
        }

        return isSuccessful
    }
}