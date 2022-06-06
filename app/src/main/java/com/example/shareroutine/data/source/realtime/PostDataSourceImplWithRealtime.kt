package com.example.shareroutine.data.source.realtime

import com.example.shareroutine.data.source.PostDataSource
import com.example.shareroutine.data.source.realtime.model.*
import com.example.shareroutine.di.PostDatabaseRef
import com.example.shareroutine.di.RoutineDatabaseRef
import com.example.shareroutine.di.TopDatabaseRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PostDataSourceImplWithRealtime @Inject constructor(
    @TopDatabaseRef private val topDbRef: DatabaseReference,
    @PostDatabaseRef private val postDbRef: DatabaseReference,
    @RoutineDatabaseRef private val routineDbRef: DatabaseReference
) : PostDataSource {
    override suspend fun insert(post: RealtimeDBModelPostWithRoutine) {
        val postData = post.post!!
        val routine = post.routineWithTodo!!

        val newKey = postDbRef.push().key!!

        postData.id = newKey
        postDbRef.child(newKey).setValue(postData).await()
        routineDbRef.child(newKey).setValue(routine).await()
    }

    override suspend fun update(post: RealtimeDBModelPostWithRoutine) {
        val postData = post.post!!
        val routine = post.routineWithTodo!!

        val postItem: HashMap<String, RealtimeDBModelPost> = hashMapOf()
        val routineItem: HashMap<String, RealtimeDBModelRoutineWithTodo> = hashMapOf()

        postData.id?.let {
            postItem[it] = postData
            routineItem[it] = routine
        }

        postDbRef.updateChildren(postItem as Map<String, Any>).await()
        routineDbRef.updateChildren(routineItem as Map<String, Any>).await()
    }

    override suspend fun delete(post: RealtimeDBModelPostWithRoutine) {
        val postData = post.post!!

        postData.id?.let {
            postDbRef.child(it).removeValue().await()
            routineDbRef.child(it).removeValue().await()
        }
    }

    override fun getAllPostList() = callbackFlow<State<List<RealtimeDBModelPostWithRoutine>>> {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val postsWithRoutine = mutableListOf<RealtimeDBModelPostWithRoutine>()

                val posts = snapshot.child("community/posts").children.map { postSnapshot ->
                    postSnapshot.getValue(RealtimeDBModelPost::class.java)!!
                }

                posts.map { dbPost ->
                    val postWithRoutine = RealtimeDBModelPostWithRoutine()

                    val routine = snapshot.child("routines").child(dbPost.id!!).getValue(RealtimeDBModelRoutineWithTodo::class.java)!!

                    postWithRoutine.post = dbPost
                    postWithRoutine.routineWithTodo = routine

                    postsWithRoutine.add(postWithRoutine)
                }

                trySend(State.success(postsWithRoutine))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(State.failed(error.message))
            }
        }

        topDbRef.addValueEventListener(listener)

        awaitClose {
            topDbRef.removeEventListener(listener)
        }
    }
}