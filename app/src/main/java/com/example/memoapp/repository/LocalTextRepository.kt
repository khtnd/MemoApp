package com.example.memoapp.repository

import com.example.memoapp.datasource.LocalTextDataSource
import javax.inject.Inject

class LocalTextRepository @Inject constructor(val dataSource: LocalTextDataSource) : TextRepository {

    override suspend fun save(name: String, content: String): Boolean {
        return dataSource.save(name, content)
    }

    override suspend fun load(name: String): Result<String> {
        return dataSource.load(name)
    }

    override suspend fun fileList(): Result<List<String>> {
        return dataSource.list()
    }
}