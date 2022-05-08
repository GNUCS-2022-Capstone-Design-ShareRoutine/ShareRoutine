package com.example.shareroutine.data.mapper

import com.example.shareroutine.data.source.realtime.model.RealtimeDBModelPost
import com.example.shareroutine.domain.model.Post
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

// TODO("루틴 관련 연결 추가 필요")
object PostMapper {
    fun mapperToPost(post: RealtimeDBModelPost): Post {
        return Post(
            title = post.title,
            userId = post.userId,
            liked = post.liked,
            downloaded = post.downloaded,
            description = post.description,
            dateTime = ZonedDateTime.ofInstant(
                Instant.ofEpochMilli(post.dateTime),
                ZoneId.systemDefault()
            )
        )
    }

    fun mapperToRealtimeDBModelPost(post: Post): RealtimeDBModelPost {
        return RealtimeDBModelPost(
            title = post.title,
            userId = post.userId,
            routineId = "",
            liked = post.liked,
            downloaded = post.downloaded,
            description = post.description,
            dateTime = post.dateTime.toInstant().toEpochMilli()
        )
    }
}