package com.example.shareroutine.data.repository

import com.example.shareroutine.data.source.PostDataSource
import com.example.shareroutine.domain.model.Post
import com.example.shareroutine.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(private val dataSource: PostDataSource) : PostRepository {
    override suspend fun insert(post: Post) {
        TODO("Not yet implemented")
    }

    override suspend fun update(post: Post) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(post: Post) {
        TODO("Not yet implemented")
    }

    override fun getAllPosts(): Flow<List<Post>> {
        TODO("Not yet implemented")
    }
}