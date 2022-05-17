package com.example.shareroutine.data.source

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult

interface UserAuthDataSource {
    suspend fun getAuthResult(googleAuthCredential: AuthCredential): AuthResult
}