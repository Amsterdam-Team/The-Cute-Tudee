package com.amsterdam.cutetudee.data.repository

import com.amsterdam.cutetudee.data.local.dao.TaskDao
import com.amsterdam.cutetudee.data.mapper.toLong
import com.amsterdam.cutetudee.data.mapper.toTask
import com.amsterdam.cutetudee.data.mapper.toTaskEntity
import com.amsterdam.cutetudee.data.mapper.toTaskListFlow
import com.amsterdam.cutetudee.domain.model.Task
import com.amsterdam.cutetudee.domain.repository.TaskRepository
import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class TaskRepositoryImpl(
    private val taskDao: TaskDao,
) : TaskRepository {
    override suspend fun addTask(task: Task) {
        taskDao.addTask(task.toTaskEntity())
    }

    override suspend fun editTask(task: Task) {
        taskDao.editTask(task.toTaskEntity())
    }

    override suspend fun deleteTask(taskId: Uuid) {
        taskDao.deleteTask(taskId.toString())
    }

    override suspend fun getTaskById(taskId: Uuid): Task {
        val taskEntity = taskDao.getTaskById(taskId.toString())
        return taskEntity.toTask()
    }

    override fun getTasksByDate(date: LocalDate) =
        taskDao.getTaskByDate(date.toLong()).toTaskListFlow()

    override fun getTasksByCategoryId(categoryId: Uuid) =
        taskDao.getTasksByCategoryId(categoryId.toString()).toTaskListFlow()
}