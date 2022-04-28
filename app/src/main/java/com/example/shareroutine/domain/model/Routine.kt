package com.example.shareroutine.domain.model

enum class Term {
    NONE, DAILY, WEEKLY, MONTHLY, YEARLY
}

data class Routine(
    val name: String,
    val term: Term,
    val todos: List<Todo>
)
