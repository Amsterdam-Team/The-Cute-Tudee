package com.amsterdam.cutetudee.presentation.screens.categoryDetails

import com.amsterdam.cutetudee.domain.model.Category
import com.amsterdam.cutetudee.domain.model.Task
import kotlin.uuid.ExperimentalUuidApi


data class CategoryDetailsUiState(
    val isLoading: Boolean = true,
    val errorMessage: String = "",
    val taskUiState: List<TaskUiState> = emptyList(),
    val categoryUiState: CategoryUiState? = null
)

data class TaskUiState(
    val id: String,
    val title: String,
    val description: String,
    val priority: String,
    val status: String,
    val createdDate: String,
    val categoryId: String
)

data class CategoryUiState(
    val id: String,
    val title: String,
    val imageUrl: String,
    val isUserCreation: Boolean = true
)


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
fun Category.toCategoryUiState(): CategoryUiState = CategoryUiState(
    id = id.toString(),
    title = name,
    imageUrl = imageUrl
)