package com.example.shareroutine.ui.community

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shareroutine.data.model.Post
import com.example.shareroutine.data.model.Routine

//Temporary
class CommunityViewModel : ViewModel() {
    val postList = MutableLiveData<List<Post>>()

    private val data = arrayListOf<Post>()

    fun addPost(post: Post) {
        data.add(post)

        postList.value = data
    }

    fun removePost(post: Post) {
        data.remove(post)

        postList.value = data
    }
}