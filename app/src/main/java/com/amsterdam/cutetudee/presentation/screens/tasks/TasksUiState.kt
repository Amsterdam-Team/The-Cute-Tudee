package com.amsterdam.cutetudee.presentation.screens.tasks

import com.amsterdam.cutetudee.presentation.component.chip.tast_status.TaskStatusUi
import com.amsterdam.cutetudee.presentation.model.TaskUi
import com.amsterdam.cutetudee.presentation.screens.common.AddEditTaskUiState
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class TasksUiState @OptIn(ExperimentalUuidApi::class) constructor(
    val currentDate: LocalDate =
        Clock.System
            .now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date,
    val tasks: List<TaskUi> = emptyList(),
    val selectedDeleteTaskId: Uuid? = null,
    val selectedTask: TaskUi? = null,
    val selectedDate: LocalDate = currentDate,
    val isSelectedDateBeforeCurrentDate: Int = 0,
    val isDateDialogVisible: Boolean = false,
    val currentSelectedTaskStatusUi: TaskStatusUi = TaskStatusUi.TODO,
    val isAddTaskBottomSheetVisible: Boolean = false,
    val isDetailsBottomSheetVisible: Boolean = false,
    val isDeleteBottomSheetVisible: Boolean = false,
    val isEditBottomSheetVisible: Boolean = false,
    val addEditTaskUiState: AddEditTaskUiState = AddEditTaskUiState()

)
