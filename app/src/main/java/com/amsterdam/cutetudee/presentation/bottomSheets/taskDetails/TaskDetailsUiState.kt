package com.amsterdam.cutetudee.presentation.bottomSheets.taskDetails

import com.amsterdam.cutetudee.presentation.model.TaskUi

data class TaskDetailsUiState(
    val selectedTask: TaskUi? = null,
    val isLoading: Boolean = false,
)
