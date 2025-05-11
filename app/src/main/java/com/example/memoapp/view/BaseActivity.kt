package com.example.memoapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getTemplate().execute(this)
    }

    protected abstract fun getTemplate(): ActivityInitializeTemplate
}