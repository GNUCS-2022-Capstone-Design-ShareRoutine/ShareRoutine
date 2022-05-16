package com.example.shareroutine.data.source.realtime

import com.example.shareroutine.data.source.RoutineRemoteDataSource
import com.example.shareroutine.data.source.realtime.model.RealtimeDBModelRoutine
import com.example.shareroutine.data.source.realtime.model.RealtimeDBModelRoutineWithTodo
import com.example.shareroutine.data.source.realtime.model.RealtimeDBModelTodo
import com.example.shareroutine.di.RoutineDatabaseRef
import com.example.shareroutine.di.TodoDatabaseRef
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

// Transaction 보장을 위해 구현 변경 필요할 수도?

class RoutineDataSourceImplWithRealtime @Inject constructor(
    @RoutineDatabaseRef private val routineDbRef: DatabaseReference,
    @TodoDatabaseRef private val todoDbRef: DatabaseReference
    ) : RoutineRemoteDataSource {
    override suspend fun insert(routineWithTodo: RealtimeDBModelRoutineWithTodo) {
        val routine = routineWithTodo.routine!!
        val todos = routineWithTodo.todos

        val newKey = routineDbRef.push().key!!
        routine.id = newKey
        routineDbRef.child(newKey).setValue(routine).await()
        todoDbRef.child(newKey).setValue(todos).await()
    }

    override suspend fun delete(routineWithTodo: RealtimeDBModelRoutineWithTodo) {
        val routine = routineWithTodo.routine!!

        routine.id?.let {
            routineDbRef.child(it).removeValue().await()
            todoDbRef.child(it).removeValue().await()
        }
    }

    override suspend fun fetchRoutine(id: String): State<RealtimeDBModelRoutineWithTodo> {
        return try {
            val routineSnapshot = routineDbRef.child(id).get().await()
            val todosSnapshot = todoDbRef.child(id).get().await()

            val routine = routineSnapshot.getValue(RealtimeDBModelRoutine::class.java)!!
            val todos = mutableListOf<RealtimeDBModelTodo>()

            todosSnapshot.children.map {
                todos.add(it.getValue(RealtimeDBModelTodo::class.java)!!)
            }

            State.success(RealtimeDBModelRoutineWithTodo(routine, todos))
        } catch (e: Exception) {
            val msg = e.message!!
            State.failed(msg)
        }
    }
}