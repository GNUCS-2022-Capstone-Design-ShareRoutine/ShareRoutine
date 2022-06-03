package com.example.shareroutine.data.source.realtime.model


data class RealtimeDBModelTodo(
    var time: String? = null,
    var dayOfWeek: Int? = null,
    var day: Int? = null,
    var month: Int? = null,
    var importance: Int = 0,
    var description: String = ""
)
