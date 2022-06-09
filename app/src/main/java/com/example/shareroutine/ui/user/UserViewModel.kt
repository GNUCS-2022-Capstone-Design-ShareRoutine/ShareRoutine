package com.example.shareroutine.ui.user

import androidx.lifecycle.*
import com.example.shareroutine.domain.usecase.user.GetUserUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase
) : ViewModel() {
    private val currentUser = FirebaseAuth.getInstance().currentUser!!
    var user = getUserUseCase(currentUser.uid).asLiveData()
}