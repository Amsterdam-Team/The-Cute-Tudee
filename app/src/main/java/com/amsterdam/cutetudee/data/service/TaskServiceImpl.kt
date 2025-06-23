package com.amsterdam.cutetudee.data.service

import com.amsterdam.cutetudee.data.local.dao.TaskDao
import com.amsterdam.cutetudee.data.mapper.toLong
import com.amsterdam.cutetudee.data.mapper.toTask
import com.amsterdam.cutetudee.data.mapper.toTaskEntity
import com.amsterdam.cutetudee.data.mapper.toTaskListFlow
import com.amsterdam.cutetudee.domain.model.Task
import com.amsterdam.cutetudee.domain.model.TaskStatistics
import com.amsterdam.cutetudee.domain.service.TaskService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class TaskServiceImpl(
    private val taskDao: TaskDao,
) : TaskService {
    override suspend fun addTask(task: Task) {
        taskDao.upsertTask(task.toTaskEntity())
    }

    override suspend fun editTask(task: Task) {
        taskDao.upsertTask(task.toTaskEntity())
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

    override fun getTasksByCategoryIdAndStatus(
        categoryId: Uuid,
        status: Task.Status
    ): Flow<List<Task>> {
        return taskDao.getTasksByCategoryIdAndStatus(
            categoryId = categoryId.toString(),
            status = status.ordinal
        ).toTaskListFlow()
    }

    override fun getTaskStatistics(): Flow<TaskStatistics> = combine(
        taskDao.getTotalTaskCount(),
        taskDao.getCompletedTaskCount(),
        taskDao.getPendingTaskCount()
    ) { total, completed, pending ->
        TaskStatistics(
            totalTasks = total,
            completedTasks = completed,
            pendingTasks = pending
        )
    }

    override fun getTaskCountByCategory(): Flow<Map<Uuid, Int>> {
        return taskDao.getTaskCountByCategory().map { list ->
            list.associate { Uuid.parse(it.categoryId) to it.count }
        }
    }

}