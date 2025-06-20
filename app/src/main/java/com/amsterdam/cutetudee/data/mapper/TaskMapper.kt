package com.amsterdam.cutetudee.data.mapper

import com.amsterdam.cutetudee.data.local.entity.TaskEntity
import com.amsterdam.cutetudee.domain.model.Task
import com.amsterdam.cutetudee.domain.model.Task.Priority
import com.amsterdam.cutetudee.domain.model.Task.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun Task.toTaskEntity(): TaskEntity = TaskEntity(
    id = id.toString(),
    title = title,
    description = description,
    targetDate = targetDate.toLong(),
    priority = priority.ordinal,
    categoryId = categoryId.toString(),
    status = status.ordinal
)

@OptIn(ExperimentalUuidApi::class)
fun TaskEntity.toTask(): Task = Task(
    id = Uuid.parse(id),
    title = title,
    description = description,
    targetDate = targetDate.toLocalDate(),
    priority = Priority.entries[priority],
    categoryId = Uuid.parse(categoryId),
    status = Status.entries[status]
)

fun Flow<List<TaskEntity>>.toTaskListFlow(): Flow<List<Task>> {
    return this.map { taskEntities ->
        taskEntities.map { it.toTask() }
    }
}

fun LocalDate.toLong(): Long = toEpochDays().toLong()

fun Long.toLocalDate(): LocalDate =
    this.let { LocalDate.fromEpochDays(it.toInt()) }
