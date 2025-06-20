package com.amsterdam.cutetudee.presentation.screens.tasks

import androidx.compose.ui.graphics.painter.Painter
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import kotlinx.datetime.LocalDate

data class AddEditTaskUiState(
    val taskName: String,
    val description: String,
    val date: LocalDate,
    val priority: PriorityUi,
    val selectedCategoryId: Int,
    val categories: List<CategoryItemUiState>,
    val taskAction: TaskAction,
) {
    enum class TaskAction { ADD, EDIT }
    data class CategoryItemUiState(
        val id: Int,
        val name: String,
        val image: Painter
    )
}
