package com.example.shareroutine.data.mapper

import com.example.shareroutine.data.source.realtime.model.RealtimeDBModelPost
import com.example.shareroutine.data.source.realtime.model.RealtimeDBModelPostWithRoutine
import com.example.shareroutine.domain.model.Post
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

object PostMapper {
    fun mapperToPost(postWithRoutine: RealtimeDBModelPostWithRoutine): Post {
        val post = postWithRoutine.post!!
        val routine = postWithRoutine.routineWithTodo!!

        return Post(
            id = post.id,
            title = post.title,
            liked = post.liked,
            downloaded = post.downloaded,
            description = post.description,
            dateTime = ZonedDateTime.ofInstant(
                Instant.ofEpochMilli(post.dateTime),
                ZoneId.systemDefault()
            ),
            routine = RoutineMapper.fromRealtimeDBModelRoutineWithTodoToRoutine(routine),
            user = UserMapper.fromRealtimeDBModelUserToUser(post.user!!)
        )
    }

    fun mapperToRealtimeDBModelPost(post: Post): RealtimeDBModelPostWithRoutine {
        val postData = RealtimeDBModelPost(
            id = post.id,
            title = post.title,
            description = post.description,
            liked = post.liked,
            downloaded = post.downloaded,
            dateTime = post.dateTime.toInstant().toEpochMilli(),
            user = UserMapper.fromUserToRealtimeDBModelUser(post.user)
        )

        val routine = RoutineMapper.fromRoutineToRealtimeDBModelRoutineWithTodo(post.routine)

        return RealtimeDBModelPostWithRoutine(postData, routine)
    }
}