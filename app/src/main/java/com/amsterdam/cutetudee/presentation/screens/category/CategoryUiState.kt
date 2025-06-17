package com.amsterdam.cutetudee.presentation.screens.category


import androidx.compose.ui.graphics.painter.Painter


data class CategoryItemUiState(
    val categoryImage: Painter ,
    val categoryName: String = "",
    val badgeText: String = ""
)