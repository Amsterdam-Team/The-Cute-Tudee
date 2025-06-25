package com.amsterdam.cutetudee.data.mapper

import com.amsterdam.cutetudee.data.local.entity.CategoryEntity
import com.amsterdam.cutetudee.data.local.entity.CategoryWithTaskCount
import com.amsterdam.cutetudee.domain.entity.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun Category.toCategoryEntity(): CategoryEntity = CategoryEntity(
    id = id.toString(),
    name = name,
    imageUri = image,
    numberOfTasks = numberOfTasks,
    isUserCreated = isUserCreated
)

@OptIn(ExperimentalUuidApi::class)
fun CategoryWithTaskCount.toCategory(): Category = Category(
    id = Uuid.parse(id),
    name = name,
    image = imageUri,
    numberOfTasks = numberOfTasks,
    isUserCreated = isUserCreated
)

fun Flow<List<CategoryWithTaskCount>>.toCategoryListFlow(): Flow<List<Category>> {
    return this.map { categoriesWithTaskCount ->
        categoriesWithTaskCount.map { it.toCategory() }
    }
}