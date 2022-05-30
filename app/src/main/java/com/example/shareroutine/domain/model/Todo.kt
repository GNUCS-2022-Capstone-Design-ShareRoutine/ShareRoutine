package com.example.shareroutine.domain.model

import java.time.DayOfWeek
import java.time.LocalTime
import java.time.Month

data class Todo(
    var time: LocalTime? = null,
    var dayOfWeek: DayOfWeek? = null,
    var day: Int? = null,
    var month: Month? = null,
    var importance: Int,
    var description: String
)
