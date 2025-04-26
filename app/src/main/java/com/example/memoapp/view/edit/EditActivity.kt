package com.example.memoapp.view.edit

import android.os.Bundle
import android.view.LayoutInflater
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityEditBinding.inflate(LayoutInflater.from(this), null, true)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.viewModel = viewModel

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
    }

    private fun getFileNameExtra(): String {
        return intent.getStringExtra(KEY_FILE_NAME) ?: ""
    }
}