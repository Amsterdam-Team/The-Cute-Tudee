package com.amsterdam.cutetudee.data.repository

import com.amsterdam.cutetudee.data.local.dao.CategoryDao
import com.amsterdam.cutetudee.data.mapper.toCategoryEntity
import com.amsterdam.cutetudee.data.mapper.toCategoryListFlow
import com.amsterdam.cutetudee.domain.model.Category
import com.amsterdam.cutetudee.domain.repository.CategoryService
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class CategoryServiceImpl(
    private val categoryDao: CategoryDao,
) : CategoryService {

    override suspend fun addCategory(category: Category) {
        categoryDao.addCategory(category.toCategoryEntity())
    }

    override suspend fun editCategory(category: Category) {
        categoryDao.editCategory(category.toCategoryEntity())
    }

    override suspend fun deleteCategory(categoryId: Uuid) {
        categoryDao.deleteCategory(categoryId.toString())
    }

    override fun getAllCategories() = categoryDao.getAllCategories().toCategoryListFlow()
}