package com.amsterdam.cutetudee.presentation.model

import androidx.compose.ui.graphics.painter.BitmapPainter
import com.amsterdam.cutetudee.domain.model.Task
import com.amsterdam.cutetudee.domain.model.toPriorityUi
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import com.amsterdam.cutetudee.presentation.component.chip.tast_status.TaskStatusUi
import com.amsterdam.cutetudee.presentation.component.chip.tast_status.toTaskStatusUi
import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class TaskUi(
    val id: Uuid,
    val title: String,
    val description: String,
    val date: LocalDate,
    val priority: PriorityUi,
    val status: TaskStatusUi,
    val categoryImage: BitmapPainter,
)

@OptIn(ExperimentalUuidApi::class)
fun Task.toTaskUi(categoryImage: BitmapPainter) =
    TaskUi(
        id = id,
        title = title,
        description = description ?: "",
        date = targetDate,
        priority = priority.toPriorityUi(),
        status = status.toTaskStatusUi(),
        categoryImage = categoryImage,
    )
