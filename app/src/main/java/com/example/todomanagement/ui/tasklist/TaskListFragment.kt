package com.example.todomanagement.ui.tasklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todomanagement.R
import com.example.todomanagement.databinding.FragmentTasklistBinding

class TaskListFragment : Fragment() {

    private lateinit var taskListViewModel: TaskListViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        taskListViewModel =
                ViewModelProvider(this).get(TaskListViewModel::class.java)
        val binding: FragmentTasklistBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_tasklist, container, false)


        return binding.root
    }
}