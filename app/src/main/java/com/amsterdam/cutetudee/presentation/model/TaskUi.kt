package com.amsterdam.cutetudee.presentation.model

import com.amsterdam.cutetudee.domain.entity.Task
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import com.amsterdam.cutetudee.presentation.component.chip.priority.toPriorityUi
import com.amsterdam.cutetudee.presentation.component.chip.priority.toTaskPriority
import com.amsterdam.cutetudee.presentation.component.chip.tast_status.TaskStatusUi
import com.amsterdam.cutetudee.presentation.component.chip.tast_status.toTaskStatus
import com.amsterdam.cutetudee.presentation.component.chip.tast_status.toTaskStatusUi
import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class TaskUi(
    val id: Uuid= Uuid.random(),
    val title: String= "",
    val description: String = "",
    val date: LocalDate = LocalDate(2025, 7, 1),
    val priority: PriorityUi = PriorityUi.LOW,
    val status: TaskStatusUi = TaskStatusUi.TODO,
    val categoryUi: CategoryUi = CategoryUi(),
)

@OptIn(ExperimentalUuidApi::class)
fun Task.toTaskUi(categoryUi: CategoryUi) =
    TaskUi(
        id = id,
        title = title,
        description = description ?: "",
        date = targetDate,
        priority = priority.toPriorityUi(),
        status = status.toTaskStatusUi(),
        categoryUi = categoryUi,
    )

@OptIn(ExperimentalUuidApi::class)
fun TaskUi.toTask(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        targetDate = date,
        priority = priority.toTaskPriority(),
        status = status.toTaskStatus(),
        categoryId = categoryUi.id,
    )
}
