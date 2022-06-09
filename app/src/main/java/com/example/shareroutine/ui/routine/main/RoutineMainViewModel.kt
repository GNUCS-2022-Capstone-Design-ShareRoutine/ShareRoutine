package com.example.shareroutine.ui.routine.main

import androidx.lifecycle.*
import com.example.shareroutine.domain.model.Term
import com.example.shareroutine.domain.model.UsedTodo
import com.example.shareroutine.domain.usecase.routine.GetUsedTodoListUseCase
import com.example.shareroutine.domain.usecase.routine.UpdateUsedTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class RoutineMainViewModel @Inject constructor(
    getUsedTodoListUseCase: GetUsedTodoListUseCase,
    private val updateUsedTodoUseCase: UpdateUsedTodoUseCase
) : ViewModel() {
    private val _usedTodos = getUsedTodoListUseCase().asLiveData()
    val usedTodo get() = _usedTodos

    lateinit var currentFirstTodo: UsedTodo

    fun setAchieved(): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        currentFirstTodo.achieved = true

        viewModelScope.launch {
            updateUsedTodoUseCase(currentFirstTodo)

            result.value = true
        }

        return result
    }

    fun updateDateTime(): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        val changed = mutableListOf<UsedTodo>()

        usedTodo.value?.forEach {
            when (it.term) {
                Term.DAILY -> {
                    val start = ZonedDateTime.now()
                        .withHour(0)
                        .withMinute(0)
                        .withSecond(0)
                        .withNano(0)

                    if (start.minusDays(1).dayOfMonth == it.dateTime.dayOfMonth) {
                        it.dateTime = it.dateTime.plusDays(1)
                        it.achieved = false

                        changed.add(it)
                    }
                }
                Term.WEEKLY -> {
                    val now = ZonedDateTime.now()
                        .withHour(0)
                        .withMinute(0)
                        .withSecond(0)
                        .withNano(0)

                    val days = now.dayOfWeek.value - DayOfWeek.MONDAY.value

                    val start = now.minusDays(days.toLong())

                    if (start.minusDays(1).dayOfWeek == it.dateTime.dayOfWeek) {
                        it.dateTime = it.dateTime.plusDays(7)
                        it.achieved = false

                        changed.add(it)
                    }
                }
                Term.MONTHLY -> {
                    val start = ZonedDateTime.now()
                        .withDayOfMonth(1)
                        .withHour(0)
                        .withMinute(0)
                        .withSecond(0)
                        .withNano(0)

                    if (start.minusMonths(1).month == it.dateTime.month) {
                        it.dateTime = it.dateTime.plusMonths(1)
                        it.achieved = false

                        changed.add(it)
                    }
                }
                Term.YEARLY -> {
                    val start = ZonedDateTime.now()
                        .withDayOfYear(1)
                        .withHour(0)
                        .withMinute(0)
                        .withSecond(0)
                        .withNano(0)

                    if (start.minusYears(1).year == it.dateTime.year) {
                        it.dateTime = it.dateTime.plusYears(1)
                        it.achieved = false

                        changed.add(it)
                    }
                }
                Term.NONE -> {}
            }
        }

        changed.map {
            viewModelScope.launch {
                updateUsedTodoUseCase(it)

                result.value = true
            }
        }
        return result
    }
}