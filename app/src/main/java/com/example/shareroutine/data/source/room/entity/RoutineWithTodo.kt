package com.example.shareroutine.data.source.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class RoutineWithTodo(
    @Embedded val roomEntityRoutine: RoomEntityRoutine,
    @Relation(parentColumn = "id", entityColumn = "routineId", entity = RoomEntityTodo::class)
    val roomEntityTodos: MutableList<RoomEntityTodo>
)