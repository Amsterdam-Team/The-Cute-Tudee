package com.amsterdam.cutetudee.domain.model

import android.net.Uri
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Category(
    val id: Uuid = Uuid.random(),
    val name: String,
    val image: String,
    val numberOfTasks : Int,
    val isUserCreated: Boolean
)