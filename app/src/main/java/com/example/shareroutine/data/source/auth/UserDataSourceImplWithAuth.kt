package com.example.shareroutine.data.source.auth

import com.example.shareroutine.data.source.UserAuthDataSource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.tasks.asDeferred
import javax.inject.Inject

class UserDataSourceImplWithAuth @Inject constructor(
    private val auth: FirebaseAuth
) : UserAuthDataSource {
    override suspend fun getAuthResultAsync(googleAuthCredential: AuthCredential): Task<AuthResult> {
        return auth.signInWithCredential(googleAuthCredential)
    }
}