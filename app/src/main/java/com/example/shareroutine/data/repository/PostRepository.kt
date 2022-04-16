package com.example.shareroutine.data.repository

import com.example.shareroutine.data.model.Post
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PostRepository {
    val firebase = Firebase.database.reference

    fun writeNewPost(postId: String, download: Int, content: String, date: Int, like: Int) {
        val post = Post(postId, download, content, date, like)

        firebase.child("post").setValue(postId)
    }

}