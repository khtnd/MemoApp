package com.example.memoapp.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.memoapp.R
import com.example.memoapp.databinding.ActivityMainBinding
import com.example.memoapp.view.recyclerview.FileAdapter
import com.example.memoapp.view.recyclerview.FileData
import com.example.memoapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val adapter: FileAdapter = FileAdapter()
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initRecyclerView()

        viewModel.updateFileList()
    }

    override fun getTemplate(): ActivityInitializeTemplate {
        return object: ActivityInitializeTemplate() {
            override fun mainView(): View {
                return binding.root
            }

            override fun registerFlow() {
                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.fileListFlow.collect {
                            it.map {
                                FileData(it.value, R.drawable.textfile)
                            }.let { fileDataList ->
                                val list = fileDataList.toMutableList()
                                list.add(0, FileData("New File", R.drawable.newfile))
                                adapter.init(list)
                            }
                        }
                    }
                }
            }

            override fun registerListener() {}
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.updateFileList()
    }

    private fun initRecyclerView() {
        val layoutManger = GridLayoutManager(this, 4)
        binding.recyclerview.layoutManager = layoutManger
        binding.recyclerview.adapter = adapter
    }
}