package com.amsterdam.cutetudee.presentation.screens.tasks

import com.amsterdam.cutetudee.domain.model.Task
import com.amsterdam.cutetudee.presentation.component.chip.tast_status.TaskStatusUi
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


data class TasksUiState(
    val currentDate: LocalDate = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val notificationBadge: Int = 0,
    val tasks: List<Task> = emptyList(),
    val currentSelectedTaskStatusUi: TaskStatusUi= TaskStatusUi.TODO

)