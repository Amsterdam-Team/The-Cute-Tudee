package com.amsterdam.cutetudee.presentation.model

import android.net.Uri
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import com.amsterdam.cutetudee.domain.model.Category
import com.amsterdam.cutetudee.presentation.utils.toBitmap
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class CategoryUi(
    val id: Uuid,
    val name: String,
    val image: Uri,
    val numberOfTasks: Int,
    val isUserCreated: Boolean,
)

@OptIn(ExperimentalUuidApi::class)
fun Category.toCategoryUi() =
    CategoryUi(
        id = id,
        name = name,
        image = image,
        numberOfTasks = numberOfTasks,
        isUserCreated = isUserCreated,
    )
