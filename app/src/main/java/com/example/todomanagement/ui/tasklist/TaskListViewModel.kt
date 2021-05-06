package com.example.todomanagement.ui.tasklist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todomanagement.database.Category
import com.example.todomanagement.database.TaskRepository
import com.example.todomanagement.database.TaskRoomDatabase
import kotlinx.coroutines.launch

class TaskListViewModel(application: Application) : AndroidViewModel(application) {
    private val tasksRepository: TaskRepository =
            TaskRepository(TaskRoomDatabase.getInstance(application))

    //TODO 先列出各个分类，呈现各个分类的数据集
    private val _items = tasksRepository.getCategories()
    val items: LiveData<List<Category>> = _items

    fun addCategory(content: String) {
        viewModelScope.launch {
            tasksRepository.saveCategory(Category(content))
        }
    }
}