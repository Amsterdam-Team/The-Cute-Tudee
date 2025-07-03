package com.amsterdam.cutetudee.presentation.bottomSheets.taskDetails

import com.amsterdam.cutetudee.presentation.model.TaskUi
import kotlin.uuid.ExperimentalUuidApi

data class TaskDetailsUiState @OptIn(ExperimentalUuidApi::class) constructor(
    val selectedTask: TaskUi? = TaskUi(),
    val isLoading: Boolean = false,
)