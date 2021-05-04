package com.example.todomanagement.ui.overview

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todomanagement.database.Task

class OverviewAdapter(private val viewModel: OverviewViewModel) :
        ListAdapter<Task, RecyclerView.ViewHolder>(OverviewDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}