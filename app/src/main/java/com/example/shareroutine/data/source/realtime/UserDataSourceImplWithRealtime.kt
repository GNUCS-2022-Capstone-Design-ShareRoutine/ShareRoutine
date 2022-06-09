package com.example.shareroutine.data.source.realtime

import com.example.shareroutine.data.source.UserRemoteDataSource
import com.example.shareroutine.data.source.realtime.model.RealtimeDBModelUser
import com.example.shareroutine.di.UserDatabaseRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserDataSourceImplWithRealtime @Inject constructor(
    @UserDatabaseRef private val dbRef: DatabaseReference
) : UserRemoteDataSource {
    override suspend fun insert(user: RealtimeDBModelUser) {
        dbRef.child(user.idToken).setValue(user).await()
    }

    override suspend fun update(user: RealtimeDBModelUser) {
        val item: HashMap<String, RealtimeDBModelUser> = hashMapOf()
        user.idToken.let { item.put(it, user) }

        dbRef.updateChildren(item as Map<String, Any>).await()
    }

    override suspend fun delete(user: RealtimeDBModelUser) {
        user.idToken.let { dbRef.child(it).removeValue().await() }
    }

    override fun getUser(id: String): Flow<State<RealtimeDBModelUser>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.child(id).getValue(RealtimeDBModelUser::class.java)!!

                trySend(State.success(user))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(State.failed(error.message))
            }
        }

        dbRef.addValueEventListener(listener)

        awaitClose {
            dbRef.removeEventListener(listener)
        }
    }

    override suspend fun fetchUser(id: String): State<RealtimeDBModelUser> {
        return try {
            val snapshot = dbRef.child(id).get().await()

            if (snapshot.exists()) {
                val user = snapshot.getValue(RealtimeDBModelUser::class.java)!!
                State.success(user)
            }
            else {
                State.failed("Snapshot doesn't exist")
            }
        }
        catch (e: Exception) {
            State.failed(e.message!!)
        }
    }
}