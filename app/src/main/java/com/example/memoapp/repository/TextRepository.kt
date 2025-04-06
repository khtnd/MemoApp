package com.example.memoapp.repository

interface TextRepository {
    suspend fun save(name: String, content: String): Boolean
    suspend fun load(name: String): Result<String>
    suspend fun fileList(): Result<List<String>>
}