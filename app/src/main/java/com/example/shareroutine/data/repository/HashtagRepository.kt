package com.example.shareroutine.data.repository

import android.content.ContentValues
import android.util.Log
import com.example.shareroutine.data.model.Hashtag
import com.example.shareroutine.data.model.Post
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class HashtagRepository {
    val firebase = Firebase.database.reference

    fun writeNewHashtag(HashtagId: Int, content: String) {
        val hashtagId = Hashtag(HashtagId, content)

        firebase.child("hashtag").setValue(hashtagId)
    }

    private fun addHashtagEventListener(hashtagReference: DatabaseReference) {
        // [START post_value_event_listener]
        val hashtagListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val hashtag = dataSnapshot.getValue<Hashtag>()
                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        hashtagReference.addValueEventListener(hashtagListener)
        // [END post_value_event_listener]
    }
}