package com.example.memoapp.data.source.local

import android.content.Context
import com.example.memoapp.types.Exceptions
import com.example.memoapp.types.FileContent
import com.example.memoapp.types.FileName
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalTextDataSource @Inject constructor(@ApplicationContext context: Context) {

    private val mutex = Mutex()
    private val dir: File


    init {
        dir = File(context.filesDir.toString() + "/text")
        dir.mkdirs()
    }

    suspend fun save(name: FileName, content: FileContent): Boolean {
        mutex.withLock {
            val file = dir.resolve("${name.value}.txt")
            return try {
                file.writeText(content.value)
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    suspend fun load(name: FileName): Result<FileContent> {
        mutex.withLock {
            val file = dir.resolve("${name.value}.txt")
            return if (file.exists().not()) {
                Result.failure(Exceptions.NO_FILE)
            } else try {
                Result.success(FileContent(file.readText()))
            } catch (e: Exception) {
                Result.failure(Exceptions.LOAD_FAILURE)
            }
        }
    }

    suspend fun list(): Result<List<FileName>> {
        mutex.withLock {
            return try {
                val files = dir.listFiles()?.map { FileName(it.nameWithoutExtension) } ?: emptyList()
                Result.success(files)
            } catch (e: Exception) {
                e.printStackTrace()
                Result.failure(e)
            }
        }
    }
}