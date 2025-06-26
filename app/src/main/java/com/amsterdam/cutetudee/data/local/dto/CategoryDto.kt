package com.amsterdam.cutetudee.data.local.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Category")
data class CategoryDto(
    @PrimaryKey val id: String,
    val name: String,
    @ColumnInfo(name="image") val imageUri: String,
    val numberOfTasks: Int,
    val isUserCreated: Boolean
)