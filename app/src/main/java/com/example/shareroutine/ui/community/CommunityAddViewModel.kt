package com.example.shareroutine.ui.community

import androidx.lifecycle.*
import com.example.shareroutine.domain.model.Post
import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.usecase.InsertPostUseCase
import com.example.shareroutine.domain.usecase.routine.GetRoutineListUseCase
import com.example.shareroutine.domain.usecase.routine.UploadRoutineUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class CommunityAddViewModel @Inject constructor(
    getRoutineListUseCase: GetRoutineListUseCase,
    private val insertPostUseCase: InsertPostUseCase
) : ViewModel() {
    val routineList = getRoutineListUseCase().asLiveData()
    var selectedRoutine: Routine? = null

    fun writePost(title: String, hashTag: String, content: String): LiveData<Boolean> {
        val user = FirebaseAuth.getInstance().currentUser!!
        val post = Post(
            title = title, userId = user.uid,
            liked = 0, downloaded = 0, description = content,
            dateTime = ZonedDateTime.now(), routine = selectedRoutine!!
        )

        val isSuccessful = MutableLiveData<Boolean>()

        viewModelScope.launch {
            insertPostUseCase(post)
            isSuccessful.value = true
        }

        return isSuccessful
    }
}