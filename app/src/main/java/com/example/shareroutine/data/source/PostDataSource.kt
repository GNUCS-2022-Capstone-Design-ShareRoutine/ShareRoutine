package com.example.shareroutine.data.source

import com.example.shareroutine.data.source.realtime.State
import com.example.shareroutine.data.source.realtime.model.RealtimeDBModelPostWithRoutine
import kotlinx.coroutines.flow.Flow

interface PostDataSource {
    suspend fun insert(post: RealtimeDBModelPostWithRoutine)
    suspend fun update(post: RealtimeDBModelPostWithRoutine)
    suspend fun delete(post: RealtimeDBModelPostWithRoutine)

    fun getAllPostList(): Flow<State<List<RealtimeDBModelPostWithRoutine>>>
    fun getPostById(id: String): Flow<State<RealtimeDBModelPostWithRoutine>>
}