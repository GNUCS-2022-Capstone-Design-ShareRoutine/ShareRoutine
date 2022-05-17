package com.example.shareroutine.data.source

import com.example.shareroutine.data.source.realtime.State
import com.example.shareroutine.data.source.realtime.model.RealtimeDBModelUser

interface UserRemoteDataSource {
    suspend fun insert(user: RealtimeDBModelUser)
    suspend fun update(user: RealtimeDBModelUser)
    suspend fun delete(user: RealtimeDBModelUser)

    suspend fun fetchUser(id: String): State<RealtimeDBModelUser>
}