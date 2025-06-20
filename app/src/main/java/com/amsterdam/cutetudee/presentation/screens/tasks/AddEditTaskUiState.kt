package com.amsterdam.cutetudee.presentation.screens.tasks

import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import com.amsterdam.cutetudee.domain.model.Category
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import com.amsterdam.cutetudee.presentation.utils.toBitmap
import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi

data class AddEditTaskUiState(
    val taskName: String = "",
    val description: String = "",
    val date: LocalDate = LocalDate.fromEpochDays(1),
    val priority: PriorityUi = PriorityUi.LOW,
    val selectedCategoryId: String = "",
    val categories: List<CategoryItemUiState> = emptyList(),
    val taskAction: TaskAction = TaskAction.ADD,
) {
    enum class TaskAction { ADD, EDIT }
    data class CategoryItemUiState(
        val id: String,
        val name: String,
        val image: Painter
    )
}


@OptIn(ExperimentalUuidApi::class)
fun Category.toCategoryItemUiState(): AddEditTaskUiState.CategoryItemUiState =
    AddEditTaskUiState.CategoryItemUiState(
        id = id.toString(),
        name = name,
        image = BitmapPainter(image.toBitmap().asImageBitmap())
    )
