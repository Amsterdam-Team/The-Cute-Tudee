package com.amsterdam.cutetudee.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.amsterdam.cutetudee.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert
    suspend fun addTask(task: TaskEntity)

    @Update
    suspend fun editTask(task: TaskEntity)

    @Query("DELETE FROM Task WHERE id = :id")
    suspend fun deleteTask(id: String)

    @Query("SELECT * FROM Task WHERE id = :id")
    suspend fun getTaskById(id: String): TaskEntity

    @Query("SELECT * FROM Task WHERE targetDate = :date")
    fun getTaskByDate(date: Long): Flow<List<TaskEntity>>

    @Query("SELECT * FROM Task WHERE categoryId = :categoryId")
    fun getTasksByCategoryId(categoryId: String): Flow<List<TaskEntity>>

    @Query("SELECT * FROM Task WHERE categoryId = :categoryId AND status = :status")
    fun getTasksByCategoryIdAndStatus(categoryId: String, status: Int): Flow<List<TaskEntity>>
}