package com.example.shareroutine.domain.usecase.user

import com.example.shareroutine.domain.model.User
import com.example.shareroutine.domain.repository.UserRepository
import javax.inject.Inject

class SignInUserUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(idToken: String): User {
        return repository.signInWithGoogle(idToken)
    }
}