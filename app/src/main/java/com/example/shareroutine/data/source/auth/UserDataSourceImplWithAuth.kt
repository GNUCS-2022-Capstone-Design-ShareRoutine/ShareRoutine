package com.example.shareroutine.data.source.auth

import com.example.shareroutine.data.source.UserAuthDataSource
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserDataSourceImplWithAuth @Inject constructor(
    private val auth: FirebaseAuth
) : UserAuthDataSource {
    override suspend fun getAuthResultAsync(googleAuthCredential: AuthCredential): AuthResult? {
        return try {
            auth.signInWithCredential(googleAuthCredential).await()
        } catch (e: Exception) {
            null
        }
    }
}