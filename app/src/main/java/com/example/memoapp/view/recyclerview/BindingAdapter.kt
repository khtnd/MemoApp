package com.example.memoapp.view.recyclerview

import android.widget.ImageView
import androidx.databinding.BindingAdapter

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("imageRes")
    fun bindImageRes(imageView: ImageView, imgResId: Int) {
        imageView.setImageResource(imgResId)
    }

}