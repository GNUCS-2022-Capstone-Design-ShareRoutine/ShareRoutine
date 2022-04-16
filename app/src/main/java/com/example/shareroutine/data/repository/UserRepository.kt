package com.example.shareroutine.data.repository

import com.example.shareroutine.data.model.Post
import com.example.shareroutine.data.model.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserRepository {
    val firebase = Firebase.database.reference

    fun writeNewUser(userId: String, nickname: String, snsId: String) {
        val user = User(userId, nickname, snsId)

        firebase.child("users").setValue(userId)
    }

}

