package com.amsterdam.cutetudee.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.amsterdam.cutetudee.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Upsert
    suspend fun upsertCategory(category: CategoryEntity)

    @Query("DELETE FROM Category WHERE id = :id")
    suspend fun deleteCategory(id: String)

    @Query("SELECT * FROM Category")
    fun getAllCategories(): Flow<List<CategoryEntity>>
}