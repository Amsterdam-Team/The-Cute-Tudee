package com.amsterdam.cutetudee.domain.repository

import com.amsterdam.cutetudee.domain.model.Category
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface CategoryRepository {
    suspend fun addCategory(category: Category)
    suspend fun deleteCategory(categoryId: Uuid)
    suspend fun getAllCategories(): Flow<List<Category>>
}