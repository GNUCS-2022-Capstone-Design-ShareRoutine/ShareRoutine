package com.example.shareroutine.data.repository

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

class UserRepository {
    val firebase = Firebase.database.reference

    private lateinit var databaseRef : DatabaseReference

    fun initializeDbRef() {
        databaseRef = Firebase.database.reference
    }

    fun writeNewUser(userId: String, nickname: String, snsId: String) {

        val user = User(userId, nickname, snsId)

        firebase.child("users").setValue(userId)
    }

    private fun addUserEventListener(userReference: DatabaseReference) {
        // [START post_value_event_listener]
        val userListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val user = dataSnapshot.getValue<User>()
                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        userReference.addValueEventListener(userListener)
        // [END post_value_event_listener]
    }






}

