package com.example.shareroutine.data.model

import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class Hashtag (
    val HashtagId: Int,
    val content: String
        )