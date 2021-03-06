package com.example.shareroutine.domain.model

import java.io.Serializable
import java.time.ZonedDateTime

data class Post(
    var id: String? = null,
    var title: String,
    var liked: MutableList<String> = mutableListOf(),
    var downloaded: MutableList<String> = mutableListOf(),
    var description: String,
    var dateTime: ZonedDateTime,
    var routine: Routine,
    var user: User,
    var hashTags: List<String>
) : Serializable