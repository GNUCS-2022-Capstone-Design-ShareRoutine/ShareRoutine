package com.example.shareroutine.data.source

import com.example.shareroutine.data.source.realtime.State
import com.example.shareroutine.data.source.realtime.model.RealtimeDBModelPost
import kotlinx.coroutines.flow.Flow

interface PostDataSource {
    suspend fun insert(post: RealtimeDBModelPost)
    suspend fun update(post: RealtimeDBModelPost)
    suspend fun delete(post: RealtimeDBModelPost)

    fun getPostList(): Flow<State<List<RealtimeDBModelPost>>>
}