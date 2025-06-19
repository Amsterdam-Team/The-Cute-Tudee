package com.amsterdam.cutetudee.data.local.entity

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Category")
data class CategoryEntity(
    @PrimaryKey val id: String,
    val name: String,
    val image: Bitmap,
    val numberOfTasks: Int,
    val isUserCreated: Boolean
)