package com.amsterdam.cutetudee.presentation.screens.category


import android.graphics.Bitmap
import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.painter.Painter

data class CategoryUiState(
    val categories: List<CategoryItemUiState> = emptyList(),
    val addBottomSheet: BottomSheetState = BottomSheetState(),
    val hideBottomSheet: Boolean = true,
)

data class CategoryItemUiState(
    val categoryImage: Painter ,
    val categoryName: String = "",
    val badgeCount: String = "",
    val isAddedByUser: Boolean = false
)

data class BottomSheetState(
    val name: String = "",
    val image: Uri = Uri.EMPTY,
    val isEnabled: Boolean = false,
    val isLoading: Boolean = false,
    @StringRes val error: Int? = null
)