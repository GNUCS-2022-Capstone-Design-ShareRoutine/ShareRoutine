package com.example.shareroutine.data.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

data class Post(
    var title: String,
    var username: String
)

/*
@IgnoreExtraProperties
data class Post (
    val postId: String,
    val download: Int,
    val content: String,
    val date: Int,
    val like: Int) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "postId" to postId,
            "download" to download,
            "content" to content,
            "date" to date,
            "like" to like
        )
    }
}
*/