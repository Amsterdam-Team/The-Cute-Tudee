package com.amsterdam.cutetudee.domain.service

import com.amsterdam.cutetudee.domain.model.Category
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface CategoryService {
    suspend fun addCategory(category: Category)
    suspend fun editCategory(category: Category)
    suspend fun deleteCategory(categoryId: Uuid)
    suspend fun getCategoryById(categoryId: Uuid): Category
    fun getAllCategories(): Flow<List<Category>>
}