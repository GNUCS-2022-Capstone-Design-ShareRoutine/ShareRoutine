package com.example.shareroutine.ui.community

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shareroutine.data.model.Routine

//Temporary
class CommunityViewModel : ViewModel() {
    val routineList = MutableLiveData<List<Routine>>()

    private val data = arrayListOf<Routine>()

    fun addRoutine(routine: Routine) {
        data.add(routine)

        routineList.value = data
    }

    fun removeRoutine(routine: Routine) {
        data.remove(routine)

        routineList.value = data
    }
}