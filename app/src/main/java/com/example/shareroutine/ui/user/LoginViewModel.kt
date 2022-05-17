package com.example.shareroutine.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shareroutine.di.IoDispatcher
import com.example.shareroutine.domain.model.User
import com.example.shareroutine.domain.usecase.user.SignInUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUserUseCase: SignInUserUseCase
) : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    fun signIn(idToken: String) {
        viewModelScope.launch {
            val result = signInUserUseCase(idToken)
            _user.postValue(result)
        }
    }
}