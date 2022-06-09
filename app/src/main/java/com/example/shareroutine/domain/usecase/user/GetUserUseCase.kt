package com.example.shareroutine.domain.usecase.user

import com.example.shareroutine.domain.model.User
import com.example.shareroutine.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(id: String): Flow<User> {
        return repository.getUser(id)
    }
}