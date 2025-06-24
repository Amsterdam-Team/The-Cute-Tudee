package com.amsterdam.cutetudee.data.local.entity

data class CategoryWithTaskCount(
    val id: String,
    val name: String,
    val imageBase64: String?,
    val numberOfTasks: Int,
    val isUserCreated: Boolean
)
