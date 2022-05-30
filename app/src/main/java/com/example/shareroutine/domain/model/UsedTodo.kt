package com.example.shareroutine.domain.model

import java.time.ZonedDateTime

data class UsedTodo(
    var dateTime: ZonedDateTime,
    var importance: Int,
    var description: String,
    var achieved: Boolean,
    var routineId: Int
)
