package com.example.memoapp.viewmodel.newfile

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
class NewFileViewModel @Inject constructor(
    private val repository: TextRepository,
) : ViewModel() {

    private val _fileList = MutableStateFlow<List<String>>(emptyList())
    val fileList = _fileList.asStateFlow()

    fun update() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = repository.fileList()
            if (list.isSuccess) {
                _fileList.emit(list.getOrDefault(emptyList()))
            }
        }
    }

    fun save(name: String) {
        viewModelScope.launch {
            repository.save(name, "")
        }
    }
}