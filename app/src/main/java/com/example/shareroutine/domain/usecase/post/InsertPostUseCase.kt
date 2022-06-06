package com.example.shareroutine.domain.usecase.post

import com.example.shareroutine.domain.model.Post
import com.example.shareroutine.domain.repository.PostRepository
import javax.inject.Inject

class InsertPostUseCase @Inject constructor(private val repository: PostRepository) {
    suspend operator fun invoke(post: Post) {
        repository.insert(post)
    }
}