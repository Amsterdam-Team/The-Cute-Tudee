package com.amsterdam.cutetudee.domain.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Category(
    val id: Uuid = Uuid.random(),
    val name: String,
    val imageUrl: String,
    val numberOfTasks : Int,
    val isUserCreated: Boolean
)