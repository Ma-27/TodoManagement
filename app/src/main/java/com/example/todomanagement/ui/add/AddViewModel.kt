package com.example.todomanagement.ui.add

import android.app.Application
import android.app.NotificationManager
import androidx.core.content.ContextCompat
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
import com.example.todomanagement.util.sendNotification
import kotlinx.coroutines.launch

class AddViewModel(private val app: Application) : AndroidViewModel(app) {
    private val tasksRepository: TaskRepository =
            TaskRepository(TaskRoomDatabase.getInstance(app))

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

        //没问题了，创建任务,防止currentTime为空
        createTask(Task(currentTitle, currentDescription, currentDateTime))

        startTimer()
    }

    private fun createTask(task: Task) {
        viewModelScope.launch {
            tasksRepository.saveTask(task)
            _taskUpdatedEvent.value = Event(Unit)
        }
        _snackbarText.value = Event(R.string.task_added_succeed)
    }

    private fun startTimer() {
        //TODO 使用通知manager创建通知
        val notificationManager = ContextCompat.getSystemService(
                app, NotificationManager::class.java) as NotificationManager

        //TODO 调用util类中的方法直接发送通知
        notificationManager.sendNotification(title.value!!, description.value!!, app)
        //notificationManager.cancelNotifications()
    }
}