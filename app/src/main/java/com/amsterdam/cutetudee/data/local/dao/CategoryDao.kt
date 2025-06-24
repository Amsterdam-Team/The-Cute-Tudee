package com.amsterdam.cutetudee.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.amsterdam.cutetudee.data.local.entity.CategoryEntity
import com.amsterdam.cutetudee.data.local.entity.CategoryWithTaskCount
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Upsert
    suspend fun upsertCategory(category: CategoryEntity)

    @Query("DELETE FROM Category WHERE id = :id AND isUserCreated = 1")
    suspend fun deleteCategory(id: String)

    @Query("SELECT * FROM Category WHERE id = :id")
    suspend fun getCategoryById(id: String): CategoryEntity

    @Query("""
    SELECT
        C.id,
        C.name,
        C.image AS imageBase64,
        COUNT(T.id) AS numberOfTasks,
        C.isUserCreated
    FROM Category AS C
    LEFT JOIN Task AS T ON C.id = T.categoryId
    GROUP BY C.id, C.name, C.image, C.isUserCreated
""")
    fun getAllCategoriesWithTaskCount(): Flow<List<CategoryWithTaskCount>>
}