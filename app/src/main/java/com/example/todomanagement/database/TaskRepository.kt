package com.example.todomanagement.database

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskRepository(private val database: TaskRoomDatabase) {
    val tasks: LiveData<List<Task>> = database.taskDao.observeTasks()

    suspend fun insertSingleTask(task: Task) {
        withContext(Dispatchers.IO) {
            database.taskDao.insertTask(task)
        }
    }

    suspend fun saveTask(task: Task) {
        coroutineScope {
            launch { database.taskDao.insertTask(task) }
        }
    }
}