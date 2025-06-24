package com.amsterdam.cutetudee.presentation.screens.categoryDetails

import android.net.Uri
import com.amsterdam.cutetudee.presentation.screens.category.BottomSheetState

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
    val image: Uri = Uri.EMPTY,
    val inProgressTasksCount: Int = 0,
    val toDoTasksCount: Int = 0,
    val doneTasksCount: Int = 0,
    val isUserCreation: Boolean = true
)