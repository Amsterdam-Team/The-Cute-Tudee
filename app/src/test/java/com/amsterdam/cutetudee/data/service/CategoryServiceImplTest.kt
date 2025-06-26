package com.amsterdam.cutetudee.data.service

import com.amsterdam.cutetudee.data.local.dao.CategoryDao
import com.amsterdam.cutetudee.data.local.dto.CategoryWithTaskCount
import com.amsterdam.cutetudee.domain.model.Category
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CategoryServiceImplTest {
    private lateinit var categoryDao: CategoryDao
    private lateinit var categoryService: CategoryServiceImpl
    private val sampleCategoryWithTaskCount = CategoryWithTaskCount(
        id = "1",
        name = "Category 1",
        imageUri = "image.png",
        numberOfTasks = 5,
        isUserCreated = true
    )

    @BeforeEach
    fun setUp() {
        categoryDao = mockk(relaxed = true)
        categoryService = CategoryServiceImpl(categoryDao)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `should add category by calling upsertCategory`() = runTest {
        val category = Category(
            id = Uuid.random(),
            name = "Test Category",
            image = "img.png",
            numberOfTasks = 0,
            isUserCreated = true
        )
        coEvery { categoryDao.upsertCategory(any()) } returns Unit
        categoryService.addCategory(category)
        coVerify { categoryDao.upsertCategory(any()) }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `should edit category by calling upsertCategory`() = runTest {
        val category = Category(
            id = Uuid.random(),
            name = "Edit Category",
            image = "img2.png",
            numberOfTasks = 2,
            isUserCreated = false
        )
        coEvery { categoryDao.upsertCategory(any()) } returns Unit
        categoryService.editCategory(category)
        coVerify { categoryDao.upsertCategory(any()) }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `should delete category by calling deleteCategory`() = runTest {
        val uuid = Uuid.random()
        coEvery { categoryDao.deleteCategory(uuid.toString()) } returns Unit
        categoryService.deleteCategory(uuid)
        coVerify { categoryDao.deleteCategory(uuid.toString()) }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `should return category when category exists`() = runTest {
        val uuid = Uuid.random()
        val entity = sampleCategoryWithTaskCount.copy(id = uuid.toString())
        coEvery { categoryDao.getCategoryById(uuid.toString()) } returns entity
        val result = categoryService.getCategoryById(uuid)
        assertEquals(entity.id, result.id.toString())
        assertEquals(entity.name, result.name)
        assertEquals(entity.imageUri, result.image)
        assertEquals(entity.numberOfTasks, result.numberOfTasks)
        assertEquals(entity.isUserCreated, result.isUserCreated)
        coVerify { categoryDao.getCategoryById(uuid.toString()) }
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `should throw when getCategoryById fails`() = runTest {
        val uuid = Uuid.random()
        coEvery { categoryDao.getCategoryById(uuid.toString()) } throws Exception()
        assertThrows<Exception> {
            categoryService.getCategoryById(uuid)
        }
        coVerify { categoryDao.getCategoryById(uuid.toString()) }
    }

    @Test
    fun `getAllCategories should return flow from dao`() {
        val flow = flowOf(listOf(sampleCategoryWithTaskCount))
        coEvery { categoryDao.getAllCategoriesWithTaskCount() } returns flow
        val result = categoryService.getAllCategories()
        assertNotNull(result)
    }
} 