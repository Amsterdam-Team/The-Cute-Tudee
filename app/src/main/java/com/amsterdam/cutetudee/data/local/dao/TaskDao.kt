package com.amsterdam.cutetudee.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.amsterdam.cutetudee.data.local.entity.CategoryTaskCount
import com.amsterdam.cutetudee.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.Uuid

@Dao
interface TaskDao {
    @Upsert
    suspend fun upsertTask(task: TaskEntity)

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

    @Query("SELECT COUNT(*) FROM task")
    fun getTotalTaskCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM task WHERE status = 'COMPLETED'")
    fun getCompletedTaskCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM task WHERE status = 'PENDING'")
    fun getPendingTaskCount(): Flow<Int>

    @Query("SELECT categoryId, COUNT(*) as count FROM task GROUP BY categoryId")
    fun getTaskCountByCategory(): Flow<List<CategoryTaskCount>>
}