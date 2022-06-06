package com.example.shareroutine.data.source.realtime.model

data class RealtimeDBModelPost(
    var id: String? = null,
    var title: String = "",
    var description: String = "",
    var liked: Int = 0,
    var downloaded: Int = 0,
    var dateTime: Long = 0,
    var user: RealtimeDBModelUser? = null,
    var hashTags: List<String> = emptyList()
)