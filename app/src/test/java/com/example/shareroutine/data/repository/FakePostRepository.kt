package com.example.shareroutine.data.repository

import com.example.shareroutine.domain.model.Post
import com.example.shareroutine.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakePostRepository(var posts: MutableList<Post>? = mutableListOf()) : PostRepository {
    override suspend fun insert(post: Post) {
        posts?.add(post)
    }

    override suspend fun update(post: Post) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(post: Post) {
        posts?.remove(post)
    }

    override fun getAllPosts(): Flow<List<Post>> {
        return flow {
            posts?.toList()?.let { emit(it) }
        }
    }
}