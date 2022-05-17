package com.example.shareroutine.domain.usecase.user

import com.example.shareroutine.domain.model.User
import com.example.shareroutine.domain.repository.UserRepository
import javax.inject.Inject

class FetchUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: String): User = repository.fetchUser(id)
}