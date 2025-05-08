package com.example.memoapp.view.edit

import android.os.Bundle
import android.text.InputType
import android.text.method.KeyListener
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.memoapp.R
import com.example.memoapp.constants.KEY_FILE_NAME
import com.example.memoapp.databinding.ActivityEditBinding
import com.example.memoapp.viewmodel.edit.EditViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class EditActivity : AppCompatActivity() {

    private val viewModel: EditViewModel by viewModels()
    private lateinit var binding: ActivityEditBinding
    private lateinit var keyListener: KeyListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditBinding.inflate(LayoutInflater.from(this), null, true)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.viewModel = viewModel

        keyListener = binding.editText.keyListener

        val fileName = getFileNameExtra()
        viewModel.fileName = fileName
        viewModel.loadFile(fileName)

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