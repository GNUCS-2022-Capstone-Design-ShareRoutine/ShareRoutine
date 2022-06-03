package com.example.shareroutine.data.mapper

import com.example.shareroutine.data.source.realtime.model.RealtimeDBModelUser
import com.example.shareroutine.domain.model.User
import com.google.firebase.auth.FirebaseUser

object UserMapper {
    fun fromRealtimeDBModelUserToUser(user: RealtimeDBModelUser): User {
        return User(user.idToken, user.emailId, user.nickname)
    }

    fun fromUserToRealtimeDBModelUser(user: User): RealtimeDBModelUser {
        return RealtimeDBModelUser(user.id, user.email, user.nickname)
    }

    fun fromFirebaseUserToUser(user: FirebaseUser): User {
        return User(user.uid, user.email!!, user.uid)
    }
}