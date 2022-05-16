package com.example.shareroutine.data.source

import com.example.shareroutine.data.source.realtime.State
import com.example.shareroutine.data.source.realtime.model.RealtimeDBModelPost
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakePostDataSource(var posts: MutableList<RealtimeDBModelPost>? = mutableListOf()) : PostDataSource {
    override suspend fun insert(post: RealtimeDBModelPost) {
        posts?.add(post)
    }

    override suspend fun update(post: RealtimeDBModelPost) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(post: RealtimeDBModelPost) {
        posts?.remove(post)
    }

    override fun getAllPostList(): Flow<State<List<RealtimeDBModelPost>>> {
        return flow {
            posts?.toList()?.let { emit(State.success(it)) }
        }
    }
}