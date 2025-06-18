package com.amsterdam.cutetudee.presentation.component.task_card

import androidx.compose.ui.graphics.painter.Painter
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi

data class TaskItemUiState(
    val categoryImage: Painter,
    val priorityUi: PriorityUi = PriorityUi.LOW,
    val name: String = "",
    val description: String = "",
    val date: String = ""
)