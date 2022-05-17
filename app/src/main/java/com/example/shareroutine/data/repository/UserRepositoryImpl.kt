package com.example.shareroutine.data.repository

import com.example.shareroutine.data.mapper.UserMapper
import com.example.shareroutine.data.source.UserAuthDataSource
import com.example.shareroutine.data.source.UserRemoteDataSource
import com.example.shareroutine.data.source.realtime.State
import com.example.shareroutine.di.IoDispatcher
import com.example.shareroutine.domain.model.User
import com.example.shareroutine.domain.repository.UserRepository
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource,
    private val auth: UserAuthDataSource,
    @IoDispatcher private val ioDisPatcher: CoroutineDispatcher
) : UserRepository {
    override suspend fun insert(user: User) {
        remoteDataSource.insert(UserMapper.fromUserToRealtimeDBModelUser(user))
    }

    override suspend fun update(user: User) {
        remoteDataSource.update(UserMapper.fromUserToRealtimeDBModelUser(user))
    }

    override suspend fun delete(user: User) {
        remoteDataSource.delete(UserMapper.fromUserToRealtimeDBModelUser(user))
    }

    override suspend fun fetchUser(id: String): User? {
        return when (val user = remoteDataSource.fetchUser(id)) {
            is State.Success -> {
                UserMapper.fromRealtimeDBModelUserToUser(user.data)
            }
            is State.Failed -> null
        }
    }

    override suspend fun signInWithGoogle(idToken: String): User {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        return withContext(ioDisPatcher) {
            try {
                val result = auth.getAuthResultAsync(credential)

                UserMapper.fromFirebaseUserToUser(result?.user!!)
            }
            catch (e: Exception) {
                println(e.message)
                User("", "", "")
            }
        }
    }
}