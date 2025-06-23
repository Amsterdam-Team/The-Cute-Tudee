package com.amsterdam.cutetudee.presentation.screens.tasks

import com.amsterdam.cutetudee.presentation.component.chip.tast_status.TaskStatusUi
import com.amsterdam.cutetudee.presentation.model.TaskUi
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class TasksUiState(
    val currentDate: LocalDate =
        Clock.System
            .now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date,
    val tasks: List<TaskUi> = emptyList(),
    val filteredTasks: List<TaskUi> = emptyList(),
    val currentSelectedTaskStatusUi: TaskStatusUi = TaskStatusUi.TODO,
    val showAddTaskBottomSheet: Boolean = false,
    val showTaskDetailsBottomSheet: Boolean = false,
    val taskDetails: TaskUi? = null,
    val showDeleteTaskBottomSheet: Boolean = false,
)
