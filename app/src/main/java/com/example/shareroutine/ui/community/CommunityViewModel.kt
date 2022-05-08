package com.example.shareroutine.ui.community

import androidx.lifecycle.*
import com.example.shareroutine.data.repository.PostRepositoryImpl
import com.example.shareroutine.data.source.realtime.PostDataSourceImplWithRealtime
import com.example.shareroutine.di.IoDispatcher
import com.example.shareroutine.domain.model.Post
import com.example.shareroutine.domain.usecase.GetPostListUseCase
import com.example.shareroutine.domain.usecase.InsertPostUseCase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    getPostListUseCase: GetPostListUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _posts = getPostListUseCase().asLiveData(ioDispatcher)
    val posts: LiveData<List<Post>> get() = _posts
}