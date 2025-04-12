package com.example.memoapp.view.recyclerview

import android.app.Activity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.memoapp.view.MainActivity
import com.example.memoapp.view.newfile.NewFileActivity

data class FileData(
    val name: String,
    val imgResId: Int,
) {
    private val targetClass: Class<out AppCompatActivity> = when (name) {
        "New File" -> NewFileActivity::class.java
        else -> MainActivity::class.java
    }

    fun onClick(view: View) {
        val activity = view.context as Activity
        activity.startActivity(android.content.Intent(activity, targetClass))
    }

}
