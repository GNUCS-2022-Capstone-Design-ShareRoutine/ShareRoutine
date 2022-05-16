package com.example.shareroutine.data.mapper

import com.example.shareroutine.data.source.realtime.model.RealtimeDBModelRoutine
import com.example.shareroutine.data.source.realtime.model.RealtimeDBModelRoutineWithTodo
import com.example.shareroutine.data.source.realtime.model.RealtimeDBModelTodo
import com.example.shareroutine.data.source.room.entity.RoomEntityRoutine
import com.example.shareroutine.data.source.room.entity.RoomEntityTodo
import com.example.shareroutine.data.source.room.entity.RoutineWithTodo
import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.model.Term
import com.example.shareroutine.domain.model.Todo
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

object RoutineMapper {
    fun fromRoutineWithTodoToRoutine(routineWithTodo: RoutineWithTodo): Routine {
        val todos = routineWithTodo.roomEntityTodos.map {
            Todo(it.dateTime, it.importance, it.description, it.achieved)
        }

        val term = when (routineWithTodo.roomEntityRoutine.term) {
            0 -> Term.DAILY
            1 -> Term.WEEKLY
            2 -> Term.MONTHLY
            3 -> Term.YEARLY
            else -> Term.NONE
        }

        return Routine(routineWithTodo.roomEntityRoutine.name, term, routineWithTodo.roomEntityRoutine.isUsed, todos)
    }

    fun fromRoutineToRoutineWithTodo(routine: Routine): RoutineWithTodo {
        val todos = routine.todos.map {
            RoomEntityTodo(dateTime = it.dateTime, importance = it.importance, description = it.description, achieved = it.achieved)
        }.toMutableList()

        val term = when (routine.term) {
            Term.DAILY -> 0
            Term.WEEKLY -> 1
            Term.MONTHLY -> 2
            Term.YEARLY -> 3
            Term.NONE -> 4
        }

        val roomEntityRoutine = RoomEntityRoutine(name = routine.name, term = term, isUsed = routine.isUsed)

        return RoutineWithTodo(roomEntityRoutine, todos)
    }

    fun fromRealtimeDBModelRoutineWithTodoToRoutine(routineWithTodo: RealtimeDBModelRoutineWithTodo): Routine {
        val todos = routineWithTodo.todos.map {
            Todo(
                ZonedDateTime.ofInstant(Instant.ofEpochMilli(it.dateTime), ZoneId.systemDefault()),
                it.importance, it.description, false
            )
        }

        val routine = routineWithTodo.routine!!

        val term = when (routine.term) {
            0 -> Term.DAILY
            1 -> Term.WEEKLY
            2 -> Term.MONTHLY
            3 -> Term.YEARLY
            else -> Term.NONE
        }

        return Routine(routine.name, term, false, todos)
    }

    fun fromRoutineToRealtimeDBModelRoutineWithTodo(routine: Routine): RealtimeDBModelRoutineWithTodo {
        val term = when (routine.term) {
            Term.DAILY -> 0
            Term.WEEKLY -> 1
            Term.MONTHLY -> 2
            Term.YEARLY -> 3
            Term.NONE -> 4
        }

        val realtimeTodos = mutableListOf<RealtimeDBModelTodo>()

        val realtimeRoutine = RealtimeDBModelRoutine(name = routine.name, term = term)
        routine.todos.forEach {
            realtimeTodos.add(
                RealtimeDBModelTodo(
                    it.dateTime.toInstant().toEpochMilli(),
                    it.description, it.importance
                )
            )
        }

        return RealtimeDBModelRoutineWithTodo(realtimeRoutine, realtimeTodos)
    }
}