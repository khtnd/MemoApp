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
import com.example.memoapp.TextViewModel
import com.example.memoapp.databinding.ActivityMainBinding
import com.example.memoapp.view.recyclerview.AdapterData
import com.example.memoapp.view.recyclerview.FileAdapter
import com.example.memoapp.view.recyclerview.FileData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: TextViewModel by viewModels()
    private var number = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.save.setOnClickListener {
            viewModel.save("file$number", "test1")
            number++
        }

        binding.list.setOnClickListener {
            viewModel.list()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.list.collect {
                    binding.fileList.text = it.toString()
                }
            }
        }


//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//
//        val dataList = listOf(
//            FileData(imgResId = R.drawable.ic_launcher_foreground, name = "File1"),
//            FileData(imgResId = R.drawable.ic_launcher_foreground, name = "File2"),
//            FileData(imgResId = R.drawable.ic_launcher_foreground, name = "File3"),
//            FileData(imgResId = R.drawable.ic_launcher_foreground, name = "File4"),
//        )
//
//        val adapter = FileAdapter(dataList)
//        binding.data = AdapterData(adapter)
//
//        val layoutManger = GridLayoutManager(this, 3)
//        binding.recyclerview.layoutManager = layoutManger
    }
}