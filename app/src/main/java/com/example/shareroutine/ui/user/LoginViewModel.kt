package com.example.shareroutine.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shareroutine.domain.model.User
import com.example.shareroutine.domain.usecase.user.AuthWithFirebaseUseCase
import com.example.shareroutine.domain.usecase.user.FetchUserUseCase
import com.example.shareroutine.domain.usecase.user.InsertUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authWithFirebaseUseCase: AuthWithFirebaseUseCase,
    private val fetchUserUseCase: FetchUserUseCase,
    private val insertUserUseCase: InsertUserUseCase
) : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    fun signIn(idToken: String) {
        viewModelScope.launch {
            val user = authWithFirebaseUseCase(idToken)

            if (fetchUserUseCase(user.id) != null) {
                insertUserUseCase(user)
            }

            val result = fetchUserUseCase(user.id)

            _user.postValue(user)
        }
    }
}