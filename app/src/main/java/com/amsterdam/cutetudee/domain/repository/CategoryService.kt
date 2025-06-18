package com.amsterdam.cutetudee.domain.repository

import com.amsterdam.cutetudee.domain.model.Category
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface CategoryService {
    suspend fun addCategory(category: Category)
    suspend fun editCategory(category: Category)
    suspend fun deleteCategory(categoryId: Uuid)
    fun getAllCategories(): Flow<List<Category>>
}