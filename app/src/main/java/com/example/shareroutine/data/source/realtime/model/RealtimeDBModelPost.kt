package com.example.shareroutine.data.source.realtime.model

data class RealtimeDBModelPost(
    var id: String? = null,
    var title: String = "",
    var description: String = "",
    var liked: List<String> = listOf(),
    var downloaded: List<String> = listOf(),
    var dateTime: Long = 0,
    var user: RealtimeDBModelUser? = null,
    var hashTags: List<String> = emptyList()
)