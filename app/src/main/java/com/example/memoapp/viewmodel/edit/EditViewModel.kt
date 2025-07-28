package com.example.memoapp.viewmodel.edit

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoapp.constants.EditMode
import com.example.memoapp.repository.TextRepository
import com.example.memoapp.types.FileContent
import com.example.memoapp.types.FileName
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
) : ViewModel() {

    private val _finishActivity = MutableStateFlow(false)
    val finishActivity = _finishActivity.asStateFlow()

    private val _enabledInput = MutableStateFlow(false)
    val enabledInput = _enabledInput.asStateFlow()

    private var _originContent = ""
    val content = ObservableField("")
    var name = ""

    val showViewMenu = MutableStateFlow(true)
    val showEditMenu = MutableStateFlow(false)

    init {
        showViewMenu.update { true }
        showEditMenu.update { false }
        _enabledInput.update { false }
    }

    fun loadFile(fileName: FileName) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.load(fileName)
            }.let { result ->
                result.getOrElse { exception ->
                    FileContent(
                        exception.message ?: ""
                    )
                }.let { fileContent ->
                    content.set(fileContent.value)
                    _originContent = fileContent.value
                }
            }
        }
    }

    fun onSave(changedText: String) {
        if (name.isEmpty())
            return

        viewModelScope.launch(Dispatchers.IO) {
            repository.save(FileName(name), FileContent(changedText))
        }

        _originContent = changedText

        _finishActivity.update { true }
    }

    fun onCancel() {
        changeMode(EditMode.VIEW)

        content.set(_originContent)
    }

    fun onChange(mode: EditMode) {
        changeMode(mode)
    }

    private fun changeMode(mode: EditMode) {
        when (mode) {
            EditMode.VIEW -> {
                showViewMenu.update { true }
                showEditMenu.update { false }
                _enabledInput.update { false }
            }

            EditMode.CHANGE -> {
                showViewMenu.update { false }
                showEditMenu.update { true }
                _enabledInput.update { true }
            }
        }
    }
}