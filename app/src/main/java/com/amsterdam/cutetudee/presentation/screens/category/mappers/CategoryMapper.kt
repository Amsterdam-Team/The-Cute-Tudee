package com.amsterdam.cutetudee.presentation.screens.category.mappers

import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import com.amsterdam.cutetudee.domain.model.Category
import com.amsterdam.cutetudee.presentation.screens.category.CategoryItemUiState
import com.amsterdam.cutetudee.presentation.utils.toBitmap

fun Category.toCategoryItemUiState() = CategoryItemUiState(
    categoryImage = BitmapPainter(image.toBitmap().asImageBitmap()),
    categoryName = name,
    badgeCount = numberOfTasks.toString(),
    isAddedByUser = isUserCreated
)
