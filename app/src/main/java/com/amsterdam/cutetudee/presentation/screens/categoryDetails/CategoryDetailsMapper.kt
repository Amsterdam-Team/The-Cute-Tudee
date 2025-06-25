package com.amsterdam.cutetudee.presentation.screens.categoryDetails

import androidx.core.net.toUri
import com.amsterdam.cutetudee.domain.model.Category
import com.amsterdam.cutetudee.domain.model.Task
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun Task.toTaskUiState(): TaskUiState = TaskUiState(
    id = id.toString(),
    title = title,
    description = description.toString(),
    priority = priority.name,
    status = status.name,
    createdDate = targetDate.toString(),
    categoryId = categoryId.toString()
)

@OptIn(ExperimentalUuidApi::class)
fun Category.toCategoryItemUiState(
    toDoTasksCount: Int,
    inProgressTasksCount: Int,
    doneTasksCount: Int
): CategoryItemUiState = CategoryItemUiState(
    id = id.toString(),
    title = name,
    image = image.toUri(),
    inProgressTasksCount = inProgressTasksCount,
    toDoTasksCount = toDoTasksCount,
    doneTasksCount = doneTasksCount,
    isUserCreation = isUserCreated
)

@OptIn(ExperimentalUuidApi::class)
fun String.toUuid(): Uuid {
    return Uuid.parse(this)
}