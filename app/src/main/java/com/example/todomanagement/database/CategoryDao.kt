package com.example.todomanagement.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CategoryDao {
    /**
     * 获取所有category数据
     */
    @Query("SELECT * FROM CATEGORY ORDER BY id ASC")
    fun observeCategory(): LiveData<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)
}