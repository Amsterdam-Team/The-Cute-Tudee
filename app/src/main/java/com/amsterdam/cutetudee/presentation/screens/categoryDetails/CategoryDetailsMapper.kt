package com.amsterdam.cutetudee.presentation.screens.categoryDetails

import androidx.core.net.toUri
import com.amsterdam.cutetudee.domain.entity.Category
import com.amsterdam.cutetudee.domain.entity.Task
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
fun Category.toCategoryItemUiState(): CategoryItemUiState = CategoryItemUiState(
    id = id.toString(),
    title = name,
    image = image.toUri(),
    isUserCreation = isUserCreated
)

@OptIn(ExperimentalUuidApi::class)
fun String.toUuid(): Uuid {
    return Uuid.parse(this)
}