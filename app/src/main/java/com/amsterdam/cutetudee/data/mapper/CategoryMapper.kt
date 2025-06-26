package com.amsterdam.cutetudee.data.mapper

import com.amsterdam.cutetudee.data.local.dto.CategoryDto
import com.amsterdam.cutetudee.data.local.dto.CategoryWithTaskCountDto
import com.amsterdam.cutetudee.domain.entity.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun Category.toCategoryDto(): CategoryDto = CategoryDto(
    id = id.toString(),
    name = name,
    imageUri = image,
    numberOfTasks = numberOfTasks,
    isUserCreated = isUserCreated
)

@OptIn(ExperimentalUuidApi::class)
fun CategoryWithTaskCountDto.toCategory(): Category = Category(
    id = Uuid.parse(id),
    name = name,
    image = imageUri,
    numberOfTasks = numberOfTasks,
    isUserCreated = isUserCreated
)

fun Flow<List<CategoryWithTaskCountDto>>.toCategoryListFlow(): Flow<List<Category>> {
    return this.map { categoriesWithTaskCount ->
        categoriesWithTaskCount.map { it.toCategory() }
    }
}