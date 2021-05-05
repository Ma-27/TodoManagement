package com.example.todomanagement.ui.overview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todomanagement.R
import com.example.todomanagement.database.Task
import com.example.todomanagement.database.TaskRepository
import com.example.todomanagement.database.TaskRoomDatabase
import com.example.todomanagement.util.Event

class OverviewViewModel(application: Application) : AndroidViewModel(application) {
    //获取数据库
    private val tasksRepository = TaskRepository(TaskRoomDatabase.getInstance(application))

    //开启任务时
    private val _openTaskEvent = MutableLiveData<Event<String>>()
    val openTaskEvent: LiveData<Event<String>> = _openTaskEvent

    //snackbar显示的信息，livedata处理
    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    //
    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _items = tasksRepository.observeTasks()

    val items: LiveData<List<Task>> = _items

    private fun showSnackbarMessage(message: Int) {
        _snackbarText.value = Event(message)
    }

    fun showEditResultMessage() {
        showSnackbarMessage(R.string.successfully_saved_task_message)
    }
}