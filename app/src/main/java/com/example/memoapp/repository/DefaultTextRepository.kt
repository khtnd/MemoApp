package com.example.memoapp.repository

import com.example.memoapp.data.source.local.LocalTextDataSource
import com.example.memoapp.types.FileContent
import com.example.memoapp.types.FileName
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultTextRepository @Inject constructor(
    val dataSource: LocalTextDataSource,
    val dispatcher: CoroutineDispatcher
) : TextRepository {

    override suspend fun save(name: FileName, content: FileContent): Boolean {
        return withContext(dispatcher) {
            dataSource.save(name, content)
        }
    }

    override suspend fun load(name: FileName): Result<FileContent> {
        return withContext(dispatcher){
            dataSource.load(name)
        }
    }

    override suspend fun fileList(): Result<List<FileName>> {
        return withContext(dispatcher) {
            dataSource.list()
        }
    }
}