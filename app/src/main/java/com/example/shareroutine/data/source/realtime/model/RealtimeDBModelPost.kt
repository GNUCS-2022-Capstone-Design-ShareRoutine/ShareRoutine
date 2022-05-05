package com.example.shareroutine.data.source.realtime.model

data class RealtimeDBModelPost(
    var id: String,
    var routineId: String,
    var userId: String,
    var description: String,
    var liked: Int,
    var downloaded: Int,
    var dateTime: Long
)