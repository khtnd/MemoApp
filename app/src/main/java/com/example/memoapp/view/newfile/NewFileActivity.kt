package com.example.memoapp.view.newfile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.memoapp.R
import com.example.memoapp.databinding.ActivityNewFileBinding
import com.example.memoapp.viewmodel.newfile.NewFileViewModel
import com.example.memoapp.vo.NewFileActivityBindingVo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewFileActivity : AppCompatActivity() {

    private val viewModel: NewFileViewModel by viewModels()
    private val bindingVo = NewFileActivityBindingVo()
    private var fileList = emptyList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityNewFileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.data = bindingVo

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.fileList.collect {
                    bindingVo.okEnabled = true
                    bindingVo.cancelEnabled = true

                    fileList = it
                }
            }
        }

        viewModel.update()

        binding.buttonOk.setOnClickListener {
            viewModel.save(binding.editText.text.toString())
            finish()
        }

        binding.buttonCancel.setOnClickListener {
            finish()
        }

        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (fileList.contains(s.toString())) {
                    bindingVo.okEnabled = false
                    bindingVo.showError = true
                } else {
                    bindingVo.okEnabled = true
                    bindingVo.showError = false
                }

                if (s.toString().isEmpty()) {
                    bindingVo.okEnabled = false
                } else {
                    bindingVo.okEnabled = true
                }
            }

        })

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}