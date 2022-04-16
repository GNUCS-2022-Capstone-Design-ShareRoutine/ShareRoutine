package com.example.shareroutine.data.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Post (
    val postId: String,
    val download: Int,
    val content: String,
    val date: Int,
    val like: Int)