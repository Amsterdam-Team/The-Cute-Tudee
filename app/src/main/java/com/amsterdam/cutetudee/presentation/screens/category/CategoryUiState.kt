package com.amsterdam.cutetudee.presentation.screens.category

import androidx.compose.ui.graphics.painter.Painter

data class CategoryScreenUiState(
    val categories: List<CategoryUiState> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessageResourceId: Int? = null
)

data class CategoryItemUiState(
    val categoryImage: Painter ,
    val categoryName: String = "",
    val badgeCount: String = ""
)

data class CategoryUiState(
    val categoryId: String,
    val categoryImage: String ,
    val categoryName: String = "",
    val badgeCount: String = ""
)