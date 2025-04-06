package com.example.memoapp.view.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.memoapp.databinding.LayoutFileBinding

class FileAdapter(private val fileData: List<FileData>) :
    RecyclerView.Adapter<FileAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = LayoutFileBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(fileData[position])
    }

    override fun getItemCount(): Int = fileData.size

    inner class ViewHolder(val binding: LayoutFileBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data : FileData) {
            binding.data = data
        }
    }
}