package com.example.shareroutine.data.repository

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.example.shareroutine.data.model.Post
import com.example.shareroutine.data.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.HashMap

class PostRepository {
    val firebase = Firebase.database.reference

    /*
        fun writeNewPost(postId: String, download: Int, content: String, date: Int, like: Int) {
        val post = Post(postId, download, content, date, like)

        firebase.child("post").setValue(postId)
    }

    private fun writeNewPost(postId: String, download: Int, content: String, date: Int, like: Int) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        val key = firebase.child("posts").push().key
        if (key == null) {
            Log.w(TAG, "Couldn't get push key for posts")
            return
        }

        val post = Post(postId, download, content, date, like)
        val postValues = post.toMap()

        val childUpdates = hashMapOf<String, Any>(
            "/posts/$key" to postValues,
            "/user-posts/$postId/$key" to postValues
        )

        firebase.updateChildren(childUpdates)
    }

    private fun addPostEventListener(postReference: DatabaseReference) {
        // [START post_value_event_listener]
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue<Post>()
                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        postReference.addValueEventListener(postListener)
        // [END post_value_event_listener]
    }
    */
}