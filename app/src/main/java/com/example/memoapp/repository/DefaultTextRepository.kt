package com.example.memoapp.repository

import com.example.memoapp.data.source.local.LocalTextDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultTextRepository @Inject constructor(
    val dataSource: LocalTextDataSource,
    val dispatcher: CoroutineDispatcher
) : TextRepository {

    override suspend fun save(name: String, content: String): Boolean {
        return withContext(dispatcher) {
            dataSource.save(name, content)
        }
    }

    override suspend fun load(name: String): Result<String> {
        return withContext(dispatcher){
            dataSource.load(name)
        }
    }

    override suspend fun fileList(): Result<List<String>> {
        return withContext(dispatcher) {
            dataSource.list()
        }
    }
}