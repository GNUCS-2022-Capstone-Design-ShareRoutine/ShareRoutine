package com.example.shareroutine.domain.repository

import com.example.shareroutine.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun insert(post: Post)
    suspend fun update(post: Post)
    suspend fun delete(post: Post)

    fun getAllPosts(): Flow<List<Post>>
}