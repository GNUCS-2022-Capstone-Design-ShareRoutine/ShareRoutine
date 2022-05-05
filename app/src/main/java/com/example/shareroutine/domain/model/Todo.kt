package com.example.shareroutine.domain.model

import java.time.ZonedDateTime

data class Todo(
    val dateTime: ZonedDateTime,
    val importance: Int,
    val description: String,
    val achieved: Boolean
)
