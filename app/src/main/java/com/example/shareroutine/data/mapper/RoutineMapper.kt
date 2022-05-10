package com.example.shareroutine.data.mapper

import com.example.shareroutine.data.source.realtime.model.RealtimeDBModelRoutine
import com.example.shareroutine.data.source.room.entity.RoomEntityRoutine
import com.example.shareroutine.data.source.room.entity.RoomEntityTodo
import com.example.shareroutine.data.source.room.entity.RoutineWithTodo
import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.model.Term
import com.example.shareroutine.domain.model.Todo

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

//    fun fromRealtimeDBModelRoutineToRoutine(routine: RealtimeDBModelRoutine): Routine {
//
//    }
//
//    fun fromRoutineToRealtimeDBModelRoutine(routine: Routine): RealtimeDBModelRoutine {
//
//    }
}