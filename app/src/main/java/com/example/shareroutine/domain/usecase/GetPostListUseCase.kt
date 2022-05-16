package com.example.shareroutine.domain.usecase

import com.example.shareroutine.domain.model.Post
import com.example.shareroutine.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostListUseCase @Inject constructor(private val repository: PostRepository) {
    operator fun invoke(): Flow<List<Post>> {
        return repository.getAllPosts()
    }
}