package com.amsterdam.cutetudee.presentation.model

import android.net.Uri
import androidx.core.net.toUri
import com.amsterdam.cutetudee.domain.entity.Category
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class CategoryUi(
    val id: Uuid = Uuid.random(),
    val name: String = "",
    val image: Uri = Uri.EMPTY,
    val numberOfTasks: Int = 0,
    val isUserCreated: Boolean = false,
)

@OptIn(ExperimentalUuidApi::class)
fun Category.toCategoryUi() =
    CategoryUi(
        id = id,
        name = name,
        image = image.toUri(),
        numberOfTasks = numberOfTasks,
        isUserCreated = isUserCreated,
    )
