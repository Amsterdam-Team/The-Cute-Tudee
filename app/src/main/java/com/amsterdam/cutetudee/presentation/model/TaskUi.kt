package com.amsterdam.cutetudee.presentation.model

import com.amsterdam.cutetudee.domain.model.Task
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import com.amsterdam.cutetudee.presentation.component.chip.priority.toPriorityUi
import com.amsterdam.cutetudee.presentation.component.chip.tast_status.TaskStatusUi
import com.amsterdam.cutetudee.presentation.component.chip.tast_status.toTaskStatusUi
import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class TaskUi(
    val id: Uuid,
    val title: String,
    val description: String,
    val date: LocalDate,
    val priority: PriorityUi,
    val status: TaskStatusUi,
    val categoryUi: CategoryUi,
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
