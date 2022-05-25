package com.example.shareroutine.ui.routine.manage.fresh

import androidx.lifecycle.ViewModel
import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.model.Term

class NewRoutineViewModel : ViewModel() {
    val routine = Routine("", Term.DAILY, false, emptyList())

    fun setTerm(term: Term) {
        routine.term = term
    }
}