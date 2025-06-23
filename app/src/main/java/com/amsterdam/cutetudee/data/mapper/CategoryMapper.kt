package com.amsterdam.cutetudee.data.mapper

import com.amsterdam.cutetudee.data.local.entity.CategoryEntity
import com.amsterdam.cutetudee.domain.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun Category.toCategoryEntity(): CategoryEntity = CategoryEntity(
    id = id.toString(),
    name = name,
    imageUri = image,
    isUserCreated = isUserCreated
)

@OptIn(ExperimentalUuidApi::class)
fun CategoryEntity.toCategory(): Category = Category(
    id = Uuid.parse(id),
    name = name,
    image = imageUri,
    numberOfTasks = 0,
    isUserCreated = isUserCreated
)

fun Flow<List<CategoryEntity>>.toCategoryListFlow(): Flow<List<Category>> {
    return this.map { categoryEntities ->
        categoryEntities.map { it.toCategory() }
    }
}