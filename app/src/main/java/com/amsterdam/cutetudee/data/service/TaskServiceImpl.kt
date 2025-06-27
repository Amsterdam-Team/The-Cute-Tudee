package com.amsterdam.cutetudee.data.service

import com.amsterdam.cutetudee.data.local.dao.TaskDao
import com.amsterdam.cutetudee.data.mapper.toLong
import com.amsterdam.cutetudee.data.mapper.toTask
import com.amsterdam.cutetudee.data.mapper.toTaskDto
import com.amsterdam.cutetudee.data.mapper.toTaskListFlow
import com.amsterdam.cutetudee.domain.entity.Task
import com.amsterdam.cutetudee.domain.service.TaskService
import com.amsterdam.cutetudee.domain.utils.TaskStatistics
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class TaskServiceImpl(
    private val taskDao: TaskDao,
) : TaskService {
    override suspend fun addTask(task: Task) {
        taskDao.upsertTask(task.toTaskDto())
    }

    override suspend fun editTask(task: Task) {
        taskDao.upsertTask(task.toTaskDto())
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

    override fun getTaskStatisticsByDate(date: LocalDate): Flow<TaskStatistics> {
        val timestamp = date.toLong()
        return combine(
            taskDao.getTotalTaskCountByDate(timestamp),
            taskDao.getTaskCountByStatusAndDate(Task.Status.DONE.ordinal, timestamp),
            taskDao.getTaskCountByStatusAndDate(Task.Status.TODO.ordinal, timestamp),
            taskDao.getTaskCountByStatusAndDate(Task.Status.IN_PROGRESS.ordinal, timestamp),
        ) { total, done, todo, inProgress ->
            TaskStatistics(
                totalTasks = total,
                doneTasks = done,
                toDoTasks = todo,
                inProgressTasks = inProgress
            )
        }
    }
}