package com.example.memoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoapp.repository.TextRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TextRepository
) : ViewModel() {

    private val _list = MutableStateFlow<List<String>>(emptyList())
    val list = _list.asStateFlow()

    fun save(name: String, content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.save(name, content)
        }
    }

    suspend fun load(name: String): String {
        val result = repository.load(name)
        return result.getOrDefault("")
    }

    fun list() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = repository.fileList()
            if (list.isSuccess) {
                launch(Dispatchers.Main) {
                    _list.emit(list.getOrDefault(emptyList()))
                }
            }
        }
    }
}