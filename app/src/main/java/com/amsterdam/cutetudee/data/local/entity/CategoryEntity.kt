package com.amsterdam.cutetudee.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Category")
data class CategoryEntity(
    @PrimaryKey val id: String,
    val name: String,
    val image: String,
    val numberOfTasks: Int,
    val isUserCreated: Boolean
)