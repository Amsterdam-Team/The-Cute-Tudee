package com.amsterdam.cutetudee.data.repository

import com.amsterdam.cutetudee.domain.model.Task
import com.amsterdam.cutetudee.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class TaskRepositoryImpl : TaskRepository {
    override suspend fun addTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(taskId: Uuid) {
        TODO("Not yet implemented")
    }

    override suspend fun getTaskById(taskId: Uuid): Task {
        TODO("Not yet implemented")
    }

    override suspend fun editTask(task: Task) {
        TODO("Not yet implemented")
    }

    override fun getTasksByDate(date: LocalDate): Flow<List<Task>> {
        TODO("Not yet implemented")
    }

    override fun getTasksByCategoryId(categoryId: Uuid): Flow<List<Task>> {
        TODO("Not yet implemented")
    }

    override fun getTasksByCategoryIdAndStatus(
        categoryId: Uuid,
        status: Task.Status
    ): Flow<List<Task>> {
        TODO("Not yet implemented")
    }

}