package com.amsterdam.cutetudee.presentation.screens.categoryDetails

import com.amsterdam.cutetudee.domain.model.Category
import com.amsterdam.cutetudee.domain.model.Task
import com.amsterdam.cutetudee.presentation.screens.category.BottomSheetState
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class CategoryDetailsUiState(
    val isLoading: Boolean = true,
    val errorMessage: String = "",
    val addBottomSheet: BottomSheetState = BottomSheetState(),
    val hideEditBottomSheet: Boolean = true,
    val showDeleteConfirmBottomSheet: Boolean = false,
    val taskUiState: List<TaskUiState> = emptyList(),
    val categoryItemUiState: CategoryItemUiState = CategoryItemUiState()
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

data class CategoryItemUiState(
    val id: String = "",
    val title: String = "",
    val image: String = "",
    val inProgressTasksCount: Int = 0,
    val toDoTasksCount: Int = 0,
    val doneTasksCount: Int = 0,
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
fun Category.toCategoryItemUiState(
    toDoTasksCount: Int,
    inProgressTasksCount: Int,
    doneTasksCount: Int
): CategoryItemUiState = CategoryItemUiState(
    id = id.toString(),
    title = name,
    image = image,
    inProgressTasksCount = inProgressTasksCount,
    toDoTasksCount = toDoTasksCount,
    doneTasksCount = doneTasksCount,
    isUserCreation = isUserCreated
)

@OptIn(ExperimentalUuidApi::class)
fun String.toUuid(): Uuid {
    return Uuid.parse(this)
}