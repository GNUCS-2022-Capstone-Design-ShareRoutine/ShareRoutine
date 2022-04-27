package com.example.shareroutine.data.source.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class RoutineWithTodo(
    @Embedded val routine: Routine,
    @Relation(parentColumn = "id", entityColumn = "routineId", entity = Todo::class)
    val todos: List<Todo>
)