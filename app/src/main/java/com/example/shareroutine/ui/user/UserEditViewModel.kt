package com.example.shareroutine.ui.user

import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shareroutine.domain.model.User
import com.example.shareroutine.domain.usecase.user.AuthWithFirebaseUseCase
import com.example.shareroutine.domain.usecase.user.FetchUserUseCase
import com.example.shareroutine.domain.usecase.user.InsertUserUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserEditViewModel @Inject constructor(
    private val authWithFirebaseUseCase: AuthWithFirebaseUseCase,
    private val fetchUserUseCase: FetchUserUseCase,
    private val insertUserUseCase: InsertUserUseCase
) : ViewModel() {
    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> get() = _user
    fun signIn(idToken: String) {
        viewModelScope.launch {
            try {
                val firebaseUser = authWithFirebaseUseCase(idToken)

                val storedUser = fetchUserUseCase(firebaseUser.id)

                if (storedUser == null) {
                    insertUserUseCase(firebaseUser)
                }

                _user.postValue(firebaseUser)
            }
            catch (e: Exception) {
                println(e.message)
            }
        }
    }

    // TODO: Implement the ViewModel
}