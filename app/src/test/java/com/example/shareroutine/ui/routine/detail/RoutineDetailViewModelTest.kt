package com.example.shareroutine.ui.routine.detail

import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.model.Term
import com.example.shareroutine.domain.model.Todo
import java.time.ZonedDateTime

class RoutineDetailViewModelTest {

    private val todo1 = Todo(ZonedDateTime.now(), 1, "Description 1", false)
    private val todo2 = Todo(ZonedDateTime.now(), 1, "Description 1", false)
    private val routine = Routine("Routine 1", Term.DAILY, true, listOf(todo1, todo2))


}