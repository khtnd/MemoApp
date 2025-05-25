package com.example.memoapp.repository

import com.example.memoapp.types.FileContent
import com.example.memoapp.types.FileName

interface TextRepository {
    suspend fun save(name: FileName, content: FileContent): Boolean
    suspend fun load(name: FileName): Result<FileContent>
    suspend fun fileList(): Result<List<FileName>>
}