package com.example.shareroutine.ui.user

import androidx.lifecycle.*
import com.example.shareroutine.domain.usecase.user.GetUserUseCase
import com.example.shareroutine.domain.usecase.user.UpdateUserUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserEditViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {
    private val currentUser = FirebaseAuth.getInstance().currentUser!!
    var user = getUserUseCase(currentUser.uid).asLiveData()

    fun changeNickname(nickname: String): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        viewModelScope.launch {
            val user = user.value!!

            user.nickname = nickname

            updateUserUseCase(user)

            result.value = true
        }

        return result
    }
}