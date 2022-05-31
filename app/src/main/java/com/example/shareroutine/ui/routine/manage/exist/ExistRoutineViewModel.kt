package com.example.shareroutine.ui.routine.manage.exist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.shareroutine.domain.model.Routine
import com.example.shareroutine.domain.usecase.routine.GetRoutineListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExistRoutineViewModel @Inject constructor(
    getRoutineListUseCase: GetRoutineListUseCase
) : ViewModel() {
    val routineList: LiveData<List<Routine>> = getRoutineListUseCase().asLiveData()
}