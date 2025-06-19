package com.amsterdam.cutetudee.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Task",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TaskEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String?,
    val targetDate: Long,
    val priority: Int,
    val categoryId: String,
    val status: Int
)