package com.example.shareroutine.ui.community.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shareroutine.di.IoDispatcher
import com.example.shareroutine.domain.model.Post
import com.example.shareroutine.domain.usecase.GetPostListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getPostListUseCase: GetPostListUseCase
) : ViewModel() {
    val data = mutableListOf<Post>()

    fun searchWithHashTag(query: String): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        viewModelScope.launch {
            val list = getPostListUseCase().first()

            list.filter { it.hashTags.contains(query) }.map {
                data.add(it)
            }

            result.value = true
        }

        return result
    }
}