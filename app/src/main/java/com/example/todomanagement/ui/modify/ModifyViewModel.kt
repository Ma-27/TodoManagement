package com.example.todomanagement.ui.modify

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todomanagement.R
import com.example.todomanagement.database.Task
import com.example.todomanagement.database.TaskRepository
import com.example.todomanagement.database.TaskRoomDatabase
import com.example.todomanagement.util.DateTimeFormatted
import com.example.todomanagement.util.DateTimeHolder
import com.example.todomanagement.util.Event
import kotlinx.coroutines.launch

class ModifyViewModel(application: Application, taskId: String) : AndroidViewModel(application) {
    private val tasksRepository: TaskRepository =
            TaskRepository(TaskRoomDatabase.getInstance(application))

    //获取当前编辑的task对象
    val task = MutableLiveData<Task>()

    //时间辅助类，使用livedata响应用户更改
    val time = MutableLiveData<DateTimeHolder>()

    //snackbar显示内容
    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    //更新内容类
    private val _taskUpdatedEvent = MutableLiveData<Event<Unit>>()
    val taskUpdatedEvent: LiveData<Event<Unit>> = _taskUpdatedEvent

    init {
        viewModelScope.launch {
            task.value = tasksRepository.getTaskById(taskId)
        }
        //将保存了的秒数转化为time和date
        if (task.value != null) {
            time.value = DateTimeFormatted.convertMillSecToDateTime(task.value!!.endTimeMilli)
        } else {
            time.value = DateTimeHolder()
        }
    }

    fun saveTask() {
        val currentTitle = task.value?.title
        val currentDescription = task.value?.description
        val currentDateTime =
                time.value?.let {
                    DateTimeFormatted.convertDateTimeToMillSec(it.date, it.hour, it.minute)
                }
        if (currentTitle == null || currentDescription == null) {
            _snackbarText.value = Event(R.string.empty_task_message)
            return
        }

        if (Task(currentTitle, currentDescription).isEmpty) {
            _snackbarText.value = Event(R.string.empty_task_message)
            return
        }

        //没问题了，更新数据
        if (task.value == null) {
            createTask(Task(currentTitle, currentDescription, currentDateTime))
        } else {
            task.value!!.endTimeMilli = currentDateTime
            updateTask(task.value!!)
        }
    }

    private fun updateTask(task: Task) {
        viewModelScope.launch {
            tasksRepository.updateTask(task)
            _taskUpdatedEvent.value = Event(Unit)
        }
        _snackbarText.value = Event(R.string.task_modified_succeed)
    }

    private fun createTask(task: Task) {
        viewModelScope.launch {
            tasksRepository.saveTask(task)
            _taskUpdatedEvent.value = Event(Unit)
        }
        _snackbarText.value = Event(R.string.task_added_succeed)
    }
}