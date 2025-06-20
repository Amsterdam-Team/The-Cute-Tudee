package com.amsterdam.cutetudee.presentation.screens.category.mappers

import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import com.amsterdam.cutetudee.domain.model.Category
import com.amsterdam.cutetudee.presentation.screens.category.CategoryUiState
import com.amsterdam.cutetudee.presentation.utils.toBitmap
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
fun Category.toCategoryItemUiState() = CategoryUiState(
    categoryId = id.toString(),
    categoryImage = BitmapPainter(image.toBitmap().asImageBitmap()),
    categoryName = name,
    badgeCount = numberOfTasks.toString(),
    isAddedByUser = isUserCreated,
)
