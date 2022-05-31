package com.example.shareroutine.data.mapper

import com.example.shareroutine.data.source.realtime.model.RealtimeDBModelRoutine
import com.example.shareroutine.data.source.realtime.model.RealtimeDBModelRoutineWithTodo
import com.example.shareroutine.data.source.realtime.model.RealtimeDBModelTodo
import com.example.shareroutine.data.source.room.entity.RoomEntityRoutine
import com.example.shareroutine.data.source.room.entity.RoomEntityTodo
import com.example.shareroutine.data.source.room.entity.RoutineWithTodo
import com.example.shareroutine.data.util.Converters
import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.model.Term
import com.example.shareroutine.domain.model.Todo

object RoutineMapper {
    fun fromRoutineWithTodoToRoutine(routineWithTodo: RoutineWithTodo): Routine {
        val todos = routineWithTodo.roomEntityTodos.map {
            val todo = Todo(importance = it.importance, description = it.description)

            when (routineWithTodo.roomEntityRoutine.term) {
                0 -> todo.time = it.time
                1 -> todo.dayOfWeek = it.dayOfWeek
                2 -> todo.day = it.day
                3 -> todo.month = it.month
                else -> {}
            }

            todo
        }

        val term = when (routineWithTodo.roomEntityRoutine.term) {
            0 -> Term.DAILY
            1 -> Term.WEEKLY
            2 -> Term.MONTHLY
            3 -> Term.YEARLY
            else -> Term.NONE
        }

        return Routine(
            routineWithTodo.roomEntityRoutine.id,
            routineWithTodo.roomEntityRoutine.name,
            routineWithTodo.roomEntityRoutine.userId,
            term,
            routineWithTodo.roomEntityRoutine.isUsed,
            todos
        )
    }

    fun fromRoutineToRoutineWithTodo(routine: Routine): RoutineWithTodo {
        val todos = routine.todos.map {
            val todo = RoomEntityTodo(importance = it.importance, description = it.description)

            when (routine.term) {
                Term.DAILY -> todo.time = it.time
                Term.WEEKLY -> todo.dayOfWeek = it.dayOfWeek
                Term.MONTHLY -> todo.day = it.day
                Term.YEARLY -> todo.month = it.month
                Term.NONE -> {}
            }

            todo

        }.toMutableList()

        val term = when (routine.term) {
            Term.DAILY -> 0
            Term.WEEKLY -> 1
            Term.MONTHLY -> 2
            Term.YEARLY -> 3
            Term.NONE -> 4
        }

        val roomEntityRoutine = RoomEntityRoutine(
            routine.id,
            routine.name,
            term,
            routine.isUsed,
            routine.userId
        )

        return RoutineWithTodo(roomEntityRoutine, todos)
    }

    fun fromRealtimeDBModelRoutineWithTodoToRoutine(routineWithTodo: RealtimeDBModelRoutineWithTodo): Routine {
        val converters = Converters()

        val todos = routineWithTodo.todos.map {
            val todo = Todo(importance = it.importance, description = it.description)

            when (routineWithTodo.routine?.term) {
                0 -> todo.time = converters.timestampToLocalTime(it.time)
                1 -> todo.dayOfWeek = converters.intToDayOfWeek(it.dayOfWeek)
                2 -> todo.day = it.day
                3 -> todo.month = converters.intToMonth(it.month)
                4 -> {}
            }

            todo
        }

        val routine = routineWithTodo.routine!!

        val term = when (routine.term) {
            0 -> Term.DAILY
            1 -> Term.WEEKLY
            2 -> Term.MONTHLY
            3 -> Term.YEARLY
            else -> Term.NONE
        }

        return Routine(name = routine.name, userId = routine.userId, term = term, isUsed = false, todos = todos)
    }

    fun fromRoutineToRealtimeDBModelRoutineWithTodo(routine: Routine): RealtimeDBModelRoutineWithTodo {
        val converters = Converters()

        val term = when (routine.term) {
            Term.DAILY -> 0
            Term.WEEKLY -> 1
            Term.MONTHLY -> 2
            Term.YEARLY -> 3
            Term.NONE -> 4
        }

        val realtimeTodos = mutableListOf<RealtimeDBModelTodo>()

        val realtimeRoutine = RealtimeDBModelRoutine(
            name = routine.name,
            term = term,
            userId = routine.userId
        )

        routine.todos.forEach {
            val todo = RealtimeDBModelTodo(importance = it.importance, description = it.description)

            when (routine.term) {
                Term.DAILY -> todo.time = converters.localTimeToTimestamp(it.time)
                Term.WEEKLY -> todo.dayOfWeek = converters.dayOfWeekToInt(it.dayOfWeek)
                Term.MONTHLY -> todo.day = it.day
                Term.YEARLY -> todo.month = converters.monthToInt(it.month)
                Term.NONE -> {}
            }

            realtimeTodos.add(todo)
        }

        return RealtimeDBModelRoutineWithTodo(realtimeRoutine, realtimeTodos)
    }
}