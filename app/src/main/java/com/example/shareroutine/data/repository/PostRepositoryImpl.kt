package com.example.shareroutine.data.repository

import com.example.shareroutine.data.mapper.PostMapper
import com.example.shareroutine.data.source.PostDataSource
import com.example.shareroutine.data.source.realtime.Result
import com.example.shareroutine.domain.model.Post
import com.example.shareroutine.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import java.lang.Exception
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(private val dataSource: PostDataSource) : PostRepository {
    override suspend fun insert(post: Post) {
        dataSource.insert(PostMapper.mapperToRealtimeDBModelPost(post))
    }

    override suspend fun update(post: Post) {
        dataSource.update(PostMapper.mapperToRealtimeDBModelPost(post))
    }

    override suspend fun delete(post: Post) {
        dataSource.delete(PostMapper.mapperToRealtimeDBModelPost(post))
    }

    override fun getAllPosts(): Flow<List<Post>> {
        return dataSource.getAllPostList().map {
            when (it) {
                is Result.Success -> it.data.map { post ->
                    PostMapper.mapperToPost(post)
                }
                is Result.Failed -> throw Exception(it.message)
            }
        }
    }
}