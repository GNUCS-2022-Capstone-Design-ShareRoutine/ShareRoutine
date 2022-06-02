package com.example.shareroutine.data.mapper

import com.example.shareroutine.data.source.room.entity.RoomEntityUsedTodo
import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.model.Term
import com.example.shareroutine.domain.model.Todo
import com.example.shareroutine.domain.model.UsedTodo
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

object UsedTodoMapper {
    fun fromRoomEntityUsedTodoToUsedTodo(usedTodo: RoomEntityUsedTodo): UsedTodo {
        val term = when (usedTodo.term) {
            0 -> Term.DAILY
            1 -> Term.WEEKLY
            2 -> Term.MONTHLY
            3 -> Term.YEARLY
            else -> Term.NONE
        }

        return UsedTodo(
            usedTodo.id, usedTodo.dateTime,
            usedTodo.importance, usedTodo.description,
            usedTodo.achieved, term, usedTodo.routineId!!
        )
    }

    fun fromUsedTodoToRoomEntityUsedTodo(usedTodo: UsedTodo): RoomEntityUsedTodo {
        val term = when (usedTodo.term) {
            Term.DAILY -> 0
            Term.WEEKLY -> 1
            Term.MONTHLY -> 2
            Term.YEARLY -> 3
            Term.NONE -> 4
        }

        return RoomEntityUsedTodo(
            usedTodo.id, usedTodo.dateTime,
            usedTodo.importance, usedTodo.description,
            usedTodo.achieved, term, usedTodo.routineId
        )
    }

    fun fromTodoToUsedTodo(todo: Todo, routine: Routine): UsedTodo? {
        val now = ZonedDateTime.now()
        val uid = routine.id!!

        return when (routine.term) {
            Term.DAILY -> {
                val localDateTime = LocalDateTime.of(
                    now.year, now.month, now.dayOfMonth,
                    todo.time?.hour!!, todo.time?.minute!!
                )

                val time = ZonedDateTime.of(localDateTime, ZoneId.of("Asia/Seoul"))

                UsedTodo(
                    dateTime = time,
                    importance = todo.importance,
                    description = todo.description,
                    achieved = false,
                    term = routine.term,
                    routineId = uid
                )
            }
            Term.WEEKLY -> {
                val localDateTime = if (todo.dayOfWeek!! == now.dayOfWeek) {
                    LocalDateTime.of(
                        now.year, now.month, now.dayOfMonth,
                        23, 59
                    )
                }
                else {
                    if (todo.dayOfWeek!! < now.dayOfWeek) {
                        val distance = now.dayOfWeek.value - todo.dayOfWeek!!.value
                        val day = now.dayOfWeek.minus(distance.toLong())

                        LocalDateTime.of(
                            now.year, now.month, day.value,
                            23, 59
                        )
                    }
                    else {
                        val distance = todo.dayOfWeek!!.value - now.dayOfWeek.value
                        val day = todo.dayOfWeek!!.minus(distance.toLong())

                        LocalDateTime.of(
                            now.year, now.month, day.value,
                            23, 59
                        )
                    }
                }

                val time = ZonedDateTime.of(localDateTime, ZoneId.of("Asia/Seoul"))

                UsedTodo(
                    dateTime = time,
                    importance = todo.importance,
                    description = todo.description,
                    achieved = false,
                    term = routine.term,
                    routineId = uid
                )
            }
            Term.MONTHLY -> {
                val localDateTime = todo.day?.let {
                    LocalDateTime.of(
                        now.year, now.month, it,
                        23, 59
                    )
                }

                val time = ZonedDateTime.of(localDateTime, ZoneId.of("Asia/Seoul"))

                UsedTodo(
                    dateTime = time,
                    importance = todo.importance,
                    description = todo.description,
                    achieved = false,
                    term = routine.term,
                    routineId = uid
                )
            }
            Term.YEARLY -> {
                val calendar = Calendar.getInstance()
                calendar.set(now.year, now.month.value - 1, 1)

                val localDateTime = todo.month?.let {
                    LocalDateTime.of(
                        now.year, todo.month, calendar.getActualMaximum(Calendar.DAY_OF_MONTH),
                        23, 59
                    )
                }

                val time = ZonedDateTime.of(localDateTime, ZoneId.of("Asia/Seoul"))

                UsedTodo(
                    dateTime = time,
                    importance = todo.importance,
                    description = todo.description,
                    achieved = false,
                    term = routine.term,
                    routineId = uid
                )
            }
            Term.NONE -> null
        }
    }
}