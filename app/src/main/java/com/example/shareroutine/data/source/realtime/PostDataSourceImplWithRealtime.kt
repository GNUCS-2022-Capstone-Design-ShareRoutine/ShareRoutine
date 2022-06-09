package com.example.shareroutine.data.source.realtime

import com.example.shareroutine.data.source.PostDataSource
import com.example.shareroutine.data.source.realtime.model.*
import com.example.shareroutine.di.PostDatabaseRef
import com.example.shareroutine.di.RoutineDatabaseRef
import com.example.shareroutine.di.TopDatabaseRef
import com.google.firebase.database.*
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

        postDbRef.updateChildren(postItem as Map<String, Any>)
        routineDbRef.updateChildren(routineItem as Map<String, Any>)
    }

    override suspend fun delete(post: RealtimeDBModelPostWithRoutine) {
        val postData = post.post!!

        postData.id?.let {
            routineDbRef.child(it).removeValue()
            postDbRef.child(it).removeValue()
        }
    }

    override fun getAllPostList() = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val postsWithRoutine = mutableListOf<RealtimeDBModelPostWithRoutine>()

                val posts = snapshot.child("community/posts").children.map { postSnapshot ->
                    postSnapshot.getValue(RealtimeDBModelPost::class.java)!!
                }

                posts.map { dbPost ->
                    val postWithRoutine = RealtimeDBModelPostWithRoutine()

                    val routine = snapshot.child("routines").child(dbPost.id!!).getValue(RealtimeDBModelRoutineWithTodo::class.java)

                    postWithRoutine.post = dbPost

                    if (routine != null) {
                        postWithRoutine.routineWithTodo = routine
                    }

                    postsWithRoutine.add(postWithRoutine)
                }

                val goodPosts = postsWithRoutine.filter {
                    it.routineWithTodo != null
                }

                trySend(State.success(goodPosts))
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

    override fun getPostById(id: String) = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val postWithRoutine = RealtimeDBModelPostWithRoutine()

                val post = snapshot.child(
                    "community/posts"
                ).child(id).getValue(RealtimeDBModelPost::class.java)

                val routine = snapshot.child(
                    "routines"
                ).child(id).getValue(RealtimeDBModelRoutineWithTodo::class.java)

                val routineData = snapshot.child("routines").child(id)
                println("DataSource : $routineData")

                postWithRoutine.post = post
                postWithRoutine.routineWithTodo = routine

                trySend(State.success(postWithRoutine))
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