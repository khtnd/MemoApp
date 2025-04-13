package com.example.memoapp.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.memoapp.R
import com.example.memoapp.viewmodel.MainViewModel
import com.example.memoapp.databinding.ActivityMainBinding
import com.example.memoapp.view.recyclerview.AdapterData
import com.example.memoapp.view.recyclerview.FileAdapter
import com.example.memoapp.view.recyclerview.FileData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val adapter: FileAdapter = FileAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val layoutManger = GridLayoutManager(this, 4)
        binding.recyclerview.layoutManager = layoutManger
        binding.data = AdapterData(adapter)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.list.collect {
                    it.map {
                        FileData(it, R.drawable.textfile)
                    }.let { fileDataList ->
                        val list = fileDataList.toMutableList()
                        list.add(0, FileData("New File", R.drawable.newfile))
                        adapter.init(list)
                    }
                }
            }
        }

        viewModel.list()
    }

    override fun onResume() {
        super.onResume()

        viewModel.list()
    }
}