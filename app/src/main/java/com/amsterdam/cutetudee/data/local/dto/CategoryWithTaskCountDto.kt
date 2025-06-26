package com.amsterdam.cutetudee.data.local.dto

data class CategoryWithTaskCountDto(
    val id: String,
    val name: String,
    val imageUri: String,
    val numberOfTasks: Int,
    val isUserCreated: Boolean
)