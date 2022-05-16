package com.example.shareroutine.ui.community

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shareroutine.domain.model.Todo

//Temporary
class TodoListViewModel : ViewModel() {
    val todoList = MutableLiveData<List<Todo>>()

    private val data = arrayListOf<Todo>()
}