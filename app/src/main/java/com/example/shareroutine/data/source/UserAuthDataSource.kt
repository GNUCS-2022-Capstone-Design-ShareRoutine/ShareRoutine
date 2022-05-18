package com.example.shareroutine.data.source

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult

interface UserAuthDataSource {
    suspend fun getAuthResultAsync(googleAuthCredential: AuthCredential): Task<AuthResult>
}