package com.example.memoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoapp.repository.TextRepository
import com.example.memoapp.types.FileName
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

    private val _fileListFlow = MutableStateFlow<List<FileName>>(emptyList())
    val fileListFlow = _fileListFlow.asStateFlow()

    fun updateFileList() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = repository.fileList()
            if (list.isSuccess) {
                launch(Dispatchers.Main) {
                    _fileListFlow.emit(list.getOrDefault(emptyList()))
                }
            }
        }
    }
}