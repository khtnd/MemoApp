package com.example.memoapp.view

import android.app.Activity
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

abstract class ActivityInitializeTemplate {
    protected abstract fun mainView(): View
    protected abstract fun registerFlow()
    protected abstract fun registerListener()

    fun execute(activity: Activity) {
        mainView().let { mainView ->
            activity.setContentView(mainView)
            setOnApplyWindowInsetsListener(mainView)
        }

        registerFlow()
        registerListener()
    }

    private fun setOnApplyWindowInsetsListener(mainView: View) {
        ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}