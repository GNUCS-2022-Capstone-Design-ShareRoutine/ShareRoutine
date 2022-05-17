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

    override suspend fun fetchUser(id: String): User {
        return when (val user = remoteDataSource.fetchUser(id)) {
            is State.Success -> {
                UserMapper.fromRealtimeDBModelUserToUser(user.data)
            }
            is State.Failed -> throw Exception(user.message)
        }
    }

    override suspend fun signInWithGoogle(idToken: String): User = withContext(ioDisPatcher) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        var user = User("", "", "")

        val result = auth.getAuthResult(credential)
        val uid = result.user?.uid!!
        val email = result.user?.email!!

        if (result.additionalUserInfo?.isNewUser == true) {
            remoteDataSource.insert(
                UserMapper.fromUserToRealtimeDBModelUser(
                    User(uid, email, uid)
                )
            )
        }
        else {
            when (val fetched = remoteDataSource.fetchUser(uid)) {
                is State.Success -> {
                    user = User(
                        fetched.data.idToken,
                        fetched.data.emailId,
                        fetched.data.nickname)
                }
                is State.Failed -> {
                    throw Exception(fetched.message)
                }
            }
        }

        return@withContext user
    }
}