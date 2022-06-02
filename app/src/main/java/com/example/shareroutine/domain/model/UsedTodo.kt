package com.example.shareroutine.domain.model

import java.time.ZonedDateTime

data class UsedTodo(
    var id: Int? = null,
    var dateTime: ZonedDateTime,
    var importance: Int,
    var description: String,
    var achieved: Boolean,
    var term: Term,
    var routineId: Int
)
