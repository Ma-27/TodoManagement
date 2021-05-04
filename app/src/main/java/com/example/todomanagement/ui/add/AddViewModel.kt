package com.example.todomanagement.ui.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todomanagement.R
import com.example.todomanagement.database.Task
import com.example.todomanagement.database.TaskRepository
import com.example.todomanagement.database.TaskRoomDatabase
import com.example.todomanagement.util.Converter
import com.example.todomanagement.util.Event
import kotlinx.coroutines.launch
import timber.log.Timber

class AddViewModel(application: Application) : AndroidViewModel(application) {
    private val tasksRepository: TaskRepository =
            TaskRepository(TaskRoomDatabase.getInstance(application))

    // 绑定title
    val title = MutableLiveData<String>()

    // 绑定内容
    val description = MutableLiveData<String>()

    //时间辅助类，使用livedata响应用户更改
    var time = MutableLiveData<DateTimeHolder>()

    //
    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    //snackbar显示内容
    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    //更新内容类
    private val _taskUpdatedEvent = MutableLiveData<Event<Unit>>()
    val taskUpdatedEvent: LiveData<Event<Unit>> = _taskUpdatedEvent

    init {
        time.value = DateTimeHolder()
    }

    fun saveTask() {
        val currentTitle = title.value
        val currentDescription = description.value
        val currentDateTime =
                time.value?.let {
                    Converter.convertDateTimeToMillSec(it.date, it.hour, it.minute)
                }

        Timber.d(currentDateTime.toString())

        if (currentTitle == null || currentDescription == null) {
            _snackbarText.value = Event(R.string.empty_task_message)
            return
        }

        if (Task(currentTitle, currentDescription).isEmpty) {
            _snackbarText.value = Event(R.string.empty_task_message)
            return
        }

        //没问题了，创建任务,防止currentTime为空
        createTask(Task(currentTitle, currentDescription, currentDateTime))
    }

    private fun createTask(task: Task) {
        viewModelScope.launch {
            tasksRepository.saveTask(task)
            _taskUpdatedEvent.value = Event(Unit)
        }
        _snackbarText.value = Event(R.string.task_added_succeed)
    }
}