package com.example.memoapp.viewmodel.edit

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoapp.repository.TextRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val repository: TextRepository,
): ViewModel() {

    private val _finishActivity = MutableStateFlow<Boolean>(false)
    val finishActivity = _finishActivity.asStateFlow()

    val content = ObservableField<String>()
    var fileName  = ""

    fun save(fileName: String, content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.save(fileName, content)
        }
    }

    fun loadFile(fileName: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.load(fileName)
            }.let { result ->
                withContext(Dispatchers.Main) {
                    if (result.isSuccess)
                        content.set(result.getOrDefault(""))
                }
            }
        }
    }

    fun onSave() {
        if (fileName.isEmpty())
            return

        viewModelScope.launch(Dispatchers.IO) {
            repository.save(fileName, content.get() ?: "")
        }

        _finishActivity.update { true }
    }

    fun onCancel() {
        _finishActivity.update { true }
    }
}