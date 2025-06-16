package com.amsterdam.cutetudee.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Task")
data class TaskEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String?,
    val targetDate: Long,
    val priority: Int,
    val categoryId: String
)