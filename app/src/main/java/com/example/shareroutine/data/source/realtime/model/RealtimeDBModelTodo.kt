package com.example.shareroutine.data.source.realtime.model

data class RealtimeDBModelTodo(
    var dateTime: Long = 0,
    var description: String = "",
    var importance: Int = 0
)
