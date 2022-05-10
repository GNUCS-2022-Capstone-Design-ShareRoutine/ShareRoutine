package com.example.shareroutine.data.source.realtime.model

data class RealtimeDBModelRoutineWithTodo(
    var routine: RealtimeDBModelRoutine? = null,
    var todos: MutableList<RealtimeDBModelTodo> = mutableListOf()
)
