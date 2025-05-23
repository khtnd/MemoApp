package com.example.memoapp.repository

import com.example.memoapp.types.FileContent
import com.example.memoapp.types.FileName

class FakeTextRepository : TextRepository {
    override suspend fun save(name: FileName, content: FileContent): Boolean {
        return true
    }

    override suspend fun load(name: FileName): Result<FileContent> {
        return Result.success(FileContent("success"))
    }

    override suspend fun fileList(): Result<List<FileName>> {
        return Result.success(emptyList<FileName>())
    }
}