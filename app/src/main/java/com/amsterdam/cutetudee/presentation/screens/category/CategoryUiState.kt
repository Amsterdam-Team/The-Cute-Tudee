package com.amsterdam.cutetudee.presentation.screens.category


import android.net.Uri
import androidx.compose.ui.graphics.painter.Painter

data class CategoryUiState(
    val categories: List<CategoryItemUiState> = emptyList(),
    val addBottomSheet: BottomSheetState = BottomSheetState(),
    val showBottomSheet: Boolean = false
)

data class CategoryItemUiState(
    val categoryImage: Painter ,
    val categoryName: String = "",
    val badgeCount: String = ""
)

data class BottomSheetState(
    val name: String = "",
    val image: Uri = Uri.EMPTY,
    val isEnabled: Boolean = false,
    val isLoading: Boolean = false
)