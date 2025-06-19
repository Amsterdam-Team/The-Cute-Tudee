package com.amsterdam.cutetudee.domain.model

import android.graphics.Bitmap
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Category(
    val id: Uuid = Uuid.random(),
    val name: String,
    val imageBitmap: Bitmap,
    val numberOfTasks : Int,
    val isUserCreated: Boolean
)