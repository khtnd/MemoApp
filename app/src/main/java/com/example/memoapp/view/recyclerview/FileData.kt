package com.example.memoapp.view.recyclerview

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.memoapp.constants.KEY_FILE_NAME
import com.example.memoapp.view.edit.EditActivity
import com.example.memoapp.view.newfile.NewFileActivity

data class FileData(
    val name: String,
    val imgResId: Int,
) {
    private val targetClass: Class<out AppCompatActivity> = when (name) {
        "New File" -> NewFileActivity::class.java
        else -> EditActivity::class.java
    }

    fun onClick(view: View) {
        val activity = view.context as Activity
        activity.startActivity(
            Intent(activity, targetClass).apply {
                putExtra(KEY_FILE_NAME, name)
            }
        )
    }

}
