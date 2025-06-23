package com.amsterdam.cutetudee.presentation.screens.category


import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.painter.Painter

data class CategoryScreenUiState(
    val categories: List<CategoryUiState> = emptyList(),
    val isLoading: Boolean = false,
    val addBottomSheet: BottomSheetState = BottomSheetState(),
    val hideBottomSheet: Boolean = true,
    val errorMessageResourceId: Int? = null
)

data class BottomSheetState(
    val name: String = "",
    val image: Uri = Uri.EMPTY,
    val painter: Painter? = null,
    val isEnabled: Boolean = false,
    val isLoading: Boolean = false,
    @StringRes val error: Int? = null
)

data class CategoryUiState(
    val categoryId: String,
    val categoryImage: Painter,
    val categoryName: String = "",
    val badgeCount: String = "",
    val isAddedByUser: Boolean = false,
)