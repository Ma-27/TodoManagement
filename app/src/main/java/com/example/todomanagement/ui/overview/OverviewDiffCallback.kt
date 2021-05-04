package com.example.todomanagement.ui.overview

import androidx.recyclerview.widget.DiffUtil
import com.example.todomanagement.database.Task

class OverviewDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }

}