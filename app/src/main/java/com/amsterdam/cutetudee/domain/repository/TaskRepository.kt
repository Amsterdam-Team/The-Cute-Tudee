package com.amsterdam.cutetudee.domain.repository

import com.amsterdam.cutetudee.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface TaskRepository {
    suspend fun addTask(task: Task)
    suspend fun deleteTask(taskId: Uuid)
    suspend fun getTaskById(taskId: Uuid): Task
    suspend fun editTask(task: Task)
    fun getTasksByDate(date: LocalDate): Flow<List<Task>>
    fun getTasksByCategoryId(categoryId: Uuid): Flow<List<Task>>
}