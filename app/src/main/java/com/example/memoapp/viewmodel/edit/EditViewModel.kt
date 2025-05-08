package com.example.memoapp.viewmodel.edit

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoapp.constants.EditMode
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

    private val _enabledInput = MutableStateFlow<Boolean>(false)
    val enabledInput = _enabledInput.asStateFlow()

    val content = ObservableField<String>()
    var fileName  = ""

    val showViewMenu = ObservableField<Boolean>()
    val showEditMenu = ObservableField<Boolean>()

    init {
        showViewMenu.set(true)
        showEditMenu.set(false)
        _enabledInput.value = false
        content.set("")
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
        changeMode(EditMode.VIEW)
    }

    fun onChange(mode: EditMode) {
        changeMode(mode)
    }

    private fun changeMode(mode: EditMode) {
        when (mode) {
            EditMode.VIEW -> {
                showViewMenu.set(true)
                showEditMenu.set(false)
                _enabledInput.value = false
            }
            EditMode.CHANGE -> {
                showViewMenu.set(false)
                showEditMenu.set(true)
                _enabledInput.value = true
            }
        }
    }
}