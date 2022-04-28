package com.example.shareroutine.data.mapper

import com.example.shareroutine.data.source.room.entity.RoutineWithTodo
import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.model.Term
import com.example.shareroutine.domain.model.Todo

object RoutineMapper {
    fun mapperToRoutine(routineWithTodo: RoutineWithTodo): Routine {
        val todos = routineWithTodo.todos.map {
            Todo(it.dateTime, it.importance, it.description, it.achieved)
        }

        val term = when (routineWithTodo.routine.term) {
            0 -> Term.DAILY
            1 -> Term.WEEKLY
            2 -> Term.MONTHLY
            3 -> Term.YEARLY
            else -> Term.NONE
        }

        return Routine(routineWithTodo.routine.name, term, todos)
    }
}