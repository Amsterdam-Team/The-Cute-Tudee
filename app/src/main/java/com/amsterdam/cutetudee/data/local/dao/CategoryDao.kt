package com.amsterdam.cutetudee.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.amsterdam.cutetudee.data.local.dto.CategoryDto
import com.amsterdam.cutetudee.data.local.dto.CategoryWithTaskCountDto
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Upsert
    suspend fun upsertCategory(category: CategoryDto)

    @Query("DELETE FROM Category WHERE id = :id AND isUserCreated = 1")
    suspend fun deleteCategory(id: String)

    @Query(
        """
    SELECT
        C.id,
        C.name,
        C.image AS imageUri,
        COUNT(T.id) AS numberOfTasks,
        C.isUserCreated
    FROM Category AS C
    LEFT JOIN Task AS T ON C.id = T.categoryId
    WHERE C.id = :id
    GROUP BY C.id, C.name, C.image, C.isUserCreated
"""
    )
    suspend fun getCategoryById(id: String): CategoryWithTaskCountDto?

    @Query(
        """
    SELECT
        C.id,
        C.name,
        C.image AS imageUri,
        COUNT(T.id) AS numberOfTasks,
        C.isUserCreated
    FROM Category AS C
    LEFT JOIN Task AS T ON C.id = T.categoryId
    GROUP BY C.id, C.name, C.image, C.isUserCreated
"""
    )
    fun getAllCategoriesWithTaskCount(): Flow<List<CategoryWithTaskCountDto>>
}