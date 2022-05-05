package com.example.shareroutine.domain.model

import java.time.ZonedDateTime

data class Post(
    val title: String,
    val username: String,
    val liked: Int,
    val downloaded: Int,
    val description: String,
    val dateTime: ZonedDateTime
)
