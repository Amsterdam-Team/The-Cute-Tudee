package com.amsterdam.cutetudee.presentation.screens.category

import androidx.compose.ui.graphics.painter.Painter
import com.amsterdam.cutetudee.presentation.component.AddCategoryBottomSheet.CategoryBottomSheetUIState

data class CategoryScreenUiState(
    val categories: List<CategoryUiState> = emptyList(),
    val isLoading: Boolean = false,
    val addBottomSheet: CategoryBottomSheetUIState = CategoryBottomSheetUIState(),
    val hideBottomSheet: Boolean = true,
    val errorMessageResourceId: Int? = null
)

data class CategoryUiState(
    val categoryId: String,
    val categoryImage: Painter,
    val categoryName: String = "",
    val badgeCount: String = "",
    val isAddedByUser: Boolean = false,
)