package com.amsterdam.cutetudee.presentation.screens.category

import android.net.Uri

data class CategoryScreenUiState(
    val categories: List<CategoryUiState> = emptyList(),
    val isLoading: Boolean = false,
    val addBottomSheet: BottomSheetState = BottomSheetState(),
    val hideBottomSheet: Boolean = true,
)

data class BottomSheetState(
    val name: String = "",
    val image: Uri = Uri.EMPTY,
    val isEnabled: Boolean = false,
    val isLoading: Boolean = false,
)

data class CategoryUiState(
    val categoryId: String,
    val categoryImage: Uri,
    val categoryName: String = "",
    val badgeCount: String = "",
    val isAddedByUser: Boolean = false,
)