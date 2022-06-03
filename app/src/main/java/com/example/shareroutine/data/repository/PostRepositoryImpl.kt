package com.example.shareroutine.data.repository

import com.example.shareroutine.data.mapper.PostMapper
import com.example.shareroutine.data.source.PostDataSource
import com.example.shareroutine.data.source.realtime.State
import com.example.shareroutine.di.IoDispatcher
import com.example.shareroutine.domain.model.Post
import com.example.shareroutine.domain.repository.PostRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val dataSource: PostDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
    ) : PostRepository {
    override suspend fun insert(post: Post) = withContext(ioDispatcher) {
        dataSource.insert(PostMapper.mapperToRealtimeDBModelPost(post))
    }

    override suspend fun update(post: Post) = withContext(ioDispatcher) {
        dataSource.update(PostMapper.mapperToRealtimeDBModelPost(post))
    }

    override suspend fun delete(post: Post) = withContext(ioDispatcher) {
        dataSource.delete(PostMapper.mapperToRealtimeDBModelPost(post))
    }

    override fun getAllPosts(): Flow<List<Post>> {
        return dataSource.getAllPostList().map {
            when (it) {
                is State.Success -> it.data.map { post ->
                    PostMapper.mapperToPost(post)
                }
                is State.Failed -> throw Exception(it.message)
            }
        }
    }
}