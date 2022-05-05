package com.example.shareroutine.data.source.realtime.dao

import com.example.shareroutine.data.source.realtime.State
import com.example.shareroutine.data.source.realtime.model.RealtimeDBModelPost
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class PostDao(private val dbRef: DatabaseReference) {
    fun insert(post: RealtimeDBModelPost) {
        TODO("Not yet implemented")
    }

    fun update(post: RealtimeDBModelPost) {
        TODO("Not yet implemented")
    }

    fun delete(post: RealtimeDBModelPost) {
        TODO("Not yet implemented")
    }

    fun getRealtimeDBModelPostList(): Flow<State<List<RealtimeDBModelPost>>> = callbackFlow {
        trySend(State.loading())

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val posts = snapshot.children.map {
                        it.getValue(RealtimeDBModelPost::class.java)!!
                    }

                    trySend(State.success(posts))
                }
                catch (t: Throwable) {
                    trySend(State.failed("Error: ${t.message}"))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(State.failed("Error: ${error.message}"))
                close()
            }
        }

        dbRef.addValueEventListener(listener)

        awaitClose {
            dbRef.removeEventListener(listener)
        }
    }
}