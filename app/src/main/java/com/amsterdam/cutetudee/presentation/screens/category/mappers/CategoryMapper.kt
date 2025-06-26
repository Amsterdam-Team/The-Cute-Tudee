package com.amsterdam.cutetudee.presentation.screens.category.mappers

import androidx.core.net.toUri
import com.amsterdam.cutetudee.domain.entity.Category
import com.amsterdam.cutetudee.presentation.screens.category.CategoryUiState
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
fun Category.toCategoryItemUiState() = CategoryUiState(
    categoryId = id.toString(),
    categoryImage = image.toUri(),
    categoryName = name,
    badgeCount = numberOfTasks.toString(),
    isAddedByUser = isUserCreated,
)
