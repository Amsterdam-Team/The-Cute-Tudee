package com.amsterdam.cutetudee.data.repository

import com.amsterdam.cutetudee.domain.model.Category
import com.amsterdam.cutetudee.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class CategoryRepositoryImpl : CategoryRepository {
    override suspend fun addCategory(category: Category) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCategory(categoryId: Uuid) {
        TODO("Not yet implemented")
    }

    override suspend fun editCategory(category: Category) {
        TODO("Not yet implemented")
    }

    override fun getAllCategories(): Flow<List<Category>> {
        TODO("Not yet implemented")
    }
}