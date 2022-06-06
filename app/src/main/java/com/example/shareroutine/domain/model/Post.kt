package com.example.shareroutine.domain.model

import java.time.ZonedDateTime

data class Post(
    val id: String? = null,
    val title: String,
    val userId: String,
    val liked: Int,
    val downloaded: Int,
    val description: String,
    val dateTime: ZonedDateTime,
    val routine: Routine
)