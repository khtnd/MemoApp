package com.example.memoapp.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.memoapp.R
import com.example.memoapp.databinding.ActivityMainBinding
import com.example.memoapp.view.recyclerview.AdapterData
import com.example.memoapp.view.recyclerview.FileAdapter
import com.example.memoapp.view.recyclerview.FileData

class MainActivity : AppCompatActivity() {
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

        val dataList = listOf(
            FileData(imgResId = R.drawable.ic_launcher_foreground, name = "File1"),
            FileData(imgResId = R.drawable.ic_launcher_foreground, name = "File2"),
            FileData(imgResId = R.drawable.ic_launcher_foreground, name = "File3"),
            FileData(imgResId = R.drawable.ic_launcher_foreground, name = "File4"),
        )

        val adapter = FileAdapter(dataList)
        binding.data = AdapterData(adapter)

        val layoutManger = GridLayoutManager(this, 3)
        binding.recyclerview.layoutManager = layoutManger
    }
}