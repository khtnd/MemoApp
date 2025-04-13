package com.example.memoapp.view.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.memoapp.databinding.LayoutFileBinding

class FileAdapter(private val fileData: MutableList<FileData> = mutableListOf<FileData>()) :
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

    fun init(data: List<FileData>) {
        fileData.clear()
        fileData.addAll(data)
        notifyDataSetChanged()
    }

    fun addFile(data: FileData) {
        fileData.add(data)
        notifyItemInserted(fileData.size - 1)
    }

    inner class ViewHolder(val binding: LayoutFileBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data : FileData) {
            binding.data = data
        }
    }
}