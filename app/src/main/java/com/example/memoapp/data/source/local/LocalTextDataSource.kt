package com.example.memoapp.data.source.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class LocalTextDataSource @Inject constructor(@ApplicationContext context: Context) {

    private val dir: File

    init {
        dir = File(context.filesDir.toString() + "/text")
        dir.mkdirs()
    }

    fun save(name: String, content: String): Boolean {
        val file = dir.resolve("$name.txt")
        return if (file.exists())
            false
        else try {
            file.writeText(content)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun load(name: String): Result<String> {
        val file = dir.resolve("$name.txt")
        return if (file.exists().not()) {
            Result.failure(Exception("파일 없음"))
        } else try {
            Result.success(file.readText())
        } catch (e: Exception) {
            Result.failure(Exception("파일 로드 실패"))
        }
    }

    fun list(): Result<List<String>> {
        return try {
            val files = dir.listFiles()?.map { it.nameWithoutExtension } ?: emptyList()
            Result.success(files)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}