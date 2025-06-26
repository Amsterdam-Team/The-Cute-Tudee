package com.amsterdam.cutetudee.data.service

import com.amsterdam.cutetudee.data.local.dao.CategoryDao
import com.amsterdam.cutetudee.data.mapper.toCategory
import com.amsterdam.cutetudee.data.mapper.toCategoryDto
import com.amsterdam.cutetudee.data.mapper.toCategoryListFlow
import com.amsterdam.cutetudee.domain.entity.Category
import com.amsterdam.cutetudee.domain.service.CategoryService
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


@OptIn(ExperimentalUuidApi::class)
class CategoryServiceImpl(
    private val categoryDao: CategoryDao,
) : CategoryService {

    override suspend fun addCategory(category: Category) {
        categoryDao.upsertCategory(category.toCategoryDto())
    }

    override suspend fun editCategory(category: Category) {
        categoryDao.upsertCategory(category.toCategoryDto())
    }

    override suspend fun deleteCategory(categoryId: Uuid) {
        categoryDao.deleteCategory(categoryId.toString())
    }

    override suspend fun getCategoryById(categoryId: Uuid): Category {
        return categoryDao.getCategoryById(categoryId.toString()).toCategory()
    }

    override fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategoriesWithTaskCount().toCategoryListFlow()
    }
}