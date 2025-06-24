package com.amsterdam.cutetudee.data.local.entity

data class CategoryWithTaskCount(
    val id: String,
    val name: String,
    val imageUri: String,
    val numberOfTasks: Int,
    val isUserCreated: Boolean
)