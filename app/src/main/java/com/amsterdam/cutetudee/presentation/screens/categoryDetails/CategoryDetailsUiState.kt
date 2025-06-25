package com.amsterdam.cutetudee.presentation.screens.categoryDetails

import android.net.Uri

data class CategoryDetailsUiState(
    val isLoading: Boolean = true,
    val categoryBottomSheetState: CategoryBottomSheetState = CategoryBottomSheetState(),
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
    val isUserCreation: Boolean = true
)

data class CategoryBottomSheetState(
    val name: String = "",
    val image: Uri = Uri.EMPTY,
    val isEnabled: Boolean = false,
    val isLoading: Boolean = false,
)