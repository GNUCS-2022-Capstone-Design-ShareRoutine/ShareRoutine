package com.example.shareroutine.data.source.realtime

import com.example.shareroutine.data.source.PostDataSource
import com.example.shareroutine.data.source.realtime.dao.PostDao
import com.example.shareroutine.data.source.realtime.model.RealtimeDBModelPost
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostDataSourceImplWithRealtime(private val dao: PostDao) : PostDataSource {
    override suspend fun insert(post: RealtimeDBModelPost) {
        dao.insert(post)
    }

    override suspend fun update(post: RealtimeDBModelPost) {
        dao.update(post)
    }

    override suspend fun delete(post: RealtimeDBModelPost) {
        dao.delete(post)
    }

    override fun getPostList(): Flow<State<List<RealtimeDBModelPost>>> {
        return dao.getRealtimeDBModelPostList()
    }
}