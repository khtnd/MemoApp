package com.example.memoapp.repository

class FakeTextRepository : TextRepository {
    override suspend fun save(name: String, content: String): Boolean {
        return true
    }

    override suspend fun load(name: String): Result<String> {
        return Result.success("success")
    }

    override suspend fun fileList(): Result<List<String>> {
        return Result.success(emptyList<String>())
    }
}