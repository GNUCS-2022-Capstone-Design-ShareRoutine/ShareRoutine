package com.example.shareroutine.domain.usecase.user

import com.example.shareroutine.domain.model.User
import com.example.shareroutine.domain.repository.UserRepository
import javax.inject.Inject

class InsertUserUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(user: User) {
        return repository.insert(user)
    }
}