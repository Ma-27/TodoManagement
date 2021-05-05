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
            launch {
                database.taskDao.insertTask(task)
            }
        }
    }

    /**
     * 观察所有task的数据
     *
     * @return 返回所有live data型数据
     */
    fun observeTasks(): LiveData<List<Task>> {
        return database.taskDao.observeTasks()
    }

    fun observeTaskById(taskId: String): LiveData<Task> {
        return database.taskDao.observeTaskById(taskId)
    }

    /**
     * 返回单个task的数据
     */
    suspend fun getTaskById(taskId: String): Task? {
        val task = database.taskDao.getTaskById(taskId)
        return task
    }

    /**
     * 更新整个任务
     */
    suspend fun updateTask(task: Task) {
        coroutineScope {
            launch {
                database.taskDao.updateTask(task)
            }
        }
    }

    suspend fun taskMarkedCompleted(taskId: String) {
        coroutineScope {
            launch {
                database.taskDao.updateCompleted(taskId, true)
            }
        }
    }

    suspend fun taskMarkedPending(taskId: String) {
        coroutineScope {
            launch {
                database.taskDao.updateCompleted(taskId, false)
            }
        }
    }
}