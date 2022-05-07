package com.example.shareroutine.domain.model

import java.time.ZonedDateTime

// TODO("루틴 관련 연결 추가 필요")
data class Post(
    val title: String,
    val userId: String,
    val liked: Int,
    val downloaded: Int,
    val description: String,
    val dateTime: ZonedDateTime
)
