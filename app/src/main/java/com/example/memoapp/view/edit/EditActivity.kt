package com.example.memoapp.view.edit

import android.os.Bundle
import android.text.method.KeyListener
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.memoapp.constants.KEY_FILE_NAME
import com.example.memoapp.databinding.ActivityEditBinding
import com.example.memoapp.types.FileName
import com.example.memoapp.view.ActivityInitializeTemplate
import com.example.memoapp.view.BaseActivity
import com.example.memoapp.viewmodel.edit.EditViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class EditActivity : BaseActivity() {

    private val viewModel: EditViewModel by viewModels()
    private val binding: ActivityEditBinding by lazy {
        ActivityEditBinding.inflate(LayoutInflater.from(this), null, true)
    }
    private lateinit var keyListener: KeyListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel

        val fileName = getFileNameExtra()
        viewModel.name = fileName
        viewModel.loadFile(FileName(fileName))
    }

    override fun getTemplate(): ActivityInitializeTemplate {
        return object: ActivityInitializeTemplate() {
            override fun mainView(): View {
                keyListener = binding.editText.keyListener
                return binding.root
            }

            override fun registerFlow() {
                lifecycleScope.launch {
                    viewModel.finishActivity.collect {
                        if (it) {
                            finish()
                        }
                    }
                }

                lifecycleScope.launch {
                    viewModel.enabledInput.collect {
                        setEnabledInput(it)
                    }
                }
            }

            override fun registerListener() { }

        }
    }

    private fun getFileNameExtra(): String {
        return intent.getStringExtra(KEY_FILE_NAME) ?: ""
    }

    private fun setEnabledInput(enabled: Boolean) {
        if (enabled) {
            setEditable(binding.editText)
        } else {
            setSelectableOnly(binding.editText)
        }
    }

    private fun setSelectableOnly(editText: EditText) {
        editText.keyListener = null
        editText.setTextIsSelectable(true)
        editText.isCursorVisible = false
    }

    private fun setEditable(editText: EditText) {
        editText.keyListener = keyListener
        editText.isCursorVisible = true
    }
}