package com.amsterdam.cutetudee.data.mapper

import com.amsterdam.cutetudee.data.local.dto.CategoryWithTaskCountDto
import com.amsterdam.cutetudee.domain.entity.Category
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CategoryMapperTest {
    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `should map Category to CategoryDto`() {
        val category = Category(
            id = Uuid.random(),
            name = "Test Category",
            image = "img.png",
            numberOfTasks = 3,
            isUserCreated = true
        )
        val entity = category.toCategoryDto()
        assertEquals(category.id.toString(), entity.id)
        assertEquals(category.name, entity.name)
        assertEquals(category.image, entity.imageUri)
        assertEquals(category.numberOfTasks, entity.numberOfTasks)
        assertEquals(category.isUserCreated, entity.isUserCreated)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `should map CategoryWithTaskCountDto to Category`() {
        val uuid = Uuid.random()
        val withCount = CategoryWithTaskCountDto(
            id = uuid.toString(),
            name = "Cat",
            imageUri = "img.png",
            numberOfTasks = 2,
            isUserCreated = false
        )
        val category = withCount.toCategory()
        assertEquals(withCount.id, category.id.toString())
        assertEquals(withCount.name, category.name)
        assertEquals(withCount.imageUri, category.image)
        assertEquals(withCount.numberOfTasks, category.numberOfTasks)
        assertEquals(withCount.isUserCreated, category.isUserCreated)
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `should map Flow List of CategoryWithTaskCountDto to Flow List of Category `() = runTest {
        val uuid = Uuid.random()
        val withCount = CategoryWithTaskCountDto(
            id = uuid.toString(),
            name = "Cat",
            imageUri = "img.png",
            numberOfTasks = 2,
            isUserCreated = false
        )
        val flow = flowOf(listOf(withCount))
        val mappedFlow = flow.toCategoryListFlow()
        mappedFlow.collect { list ->
            assertEquals(1, list.size)
            val category = list[0]
            assertEquals(withCount.id, category.id.toString())
        }
    }
} 