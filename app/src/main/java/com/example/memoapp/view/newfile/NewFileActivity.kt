package com.example.memoapp.view.newfile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.memoapp.R
import com.example.memoapp.databinding.ActivityNewFileBinding
import com.example.memoapp.view.ActivityInitializeTemplate
import com.example.memoapp.view.BaseActivity
import com.example.memoapp.viewmodel.newfile.NewFileViewModel
import com.example.memoapp.vo.NewFileActivityBindingVo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewFileActivity : BaseActivity() {

    private val viewModel: NewFileViewModel by viewModels()
    private val bindingVo = NewFileActivityBindingVo()
    private var fileList = emptyList<String>()
    private val binding: ActivityNewFileBinding by lazy {
        ActivityNewFileBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.data = bindingVo

        viewModel.update()
    }

    override fun getTemplate(): ActivityInitializeTemplate {
        return object: ActivityInitializeTemplate() {
            override fun mainView(): View {
                return binding.root
            }

            override fun registerFlow() {
                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.fileList.collect {
                            bindingVo.okEnabled = true
                            bindingVo.cancelEnabled = true

                            fileList = it
                        }
                    }
                }
            }

            override fun registerListener() {
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
            }

            override fun collectViewValues() { }

        }
    }
}