package com.example.shareroutine.domain.repository

import com.example.shareroutine.domain.model.User
import com.google.firebase.auth.AuthCredential

interface UserRepository {
    suspend fun insert(user: User)
    suspend fun update(user: User)
    suspend fun delete(user: User)

    suspend fun fetchUser(id: String): User

    suspend fun signInWithGoogle(idToken: String): User
}