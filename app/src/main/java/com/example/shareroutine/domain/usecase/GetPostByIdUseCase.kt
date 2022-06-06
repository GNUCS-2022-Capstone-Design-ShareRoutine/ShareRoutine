package com.example.shareroutine.domain.usecase

import com.example.shareroutine.domain.model.Post
import com.example.shareroutine.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostByIdUseCase @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke(id: String): Flow<Post> {
        return repository.getPostById(id)
    }
}