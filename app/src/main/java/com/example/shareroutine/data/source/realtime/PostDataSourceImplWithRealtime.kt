package com.example.shareroutine.data.source.realtime

import com.example.shareroutine.data.source.PostDataSource
import com.example.shareroutine.data.source.realtime.model.RealtimeDBModelPost
import com.example.shareroutine.di.PostDatabaseRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PostDataSourceImplWithRealtime @Inject constructor(
    @PostDatabaseRef private val dbRef: DatabaseReference
    ) : PostDataSource {
    override suspend fun insert(post: RealtimeDBModelPost) {
        val newKey = dbRef.push().key!!
        post.id = newKey
        dbRef.child(newKey).setValue(post).await()
    }

    override suspend fun update(post: RealtimeDBModelPost) {
        val item: HashMap<String, RealtimeDBModelPost> = hashMapOf()
        post.id?.let { item.put(it, post) }

        dbRef.updateChildren(item as Map<String, Any>).await()
    }

    override suspend fun delete(post: RealtimeDBModelPost) {
        post.id?.let { dbRef.child(it).removeValue().await() }
    }

    override fun getAllPostList() = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val posts = snapshot.children.map {
                    it.getValue(RealtimeDBModelPost::class.java)!!
                }
                trySend(Result.success(posts))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Result.failed(error.message))
            }
        }

        dbRef.addValueEventListener(listener)

        awaitClose {
            dbRef.removeEventListener(listener)
        }
    }
}