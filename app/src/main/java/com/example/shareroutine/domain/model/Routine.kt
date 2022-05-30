package com.example.shareroutine.domain.model

enum class Term {
    NONE, DAILY, WEEKLY, MONTHLY, YEARLY
}

data class Routine(
    var name: String,
    var term: Term,
    var isUsed: Boolean,
    var todos: List<Todo>
)
