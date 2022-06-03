package com.example.shareroutine.ui.community.search

import androidx.lifecycle.ViewModel
import com.example.shareroutine.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {
    fun searchWithHashTag(query: String) {
        println("searchWithHashTag called with query string $query")
    }
}