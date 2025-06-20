package com.amsterdam.cutetudee.presentation.bottomSheets.taskDetails

import com.amsterdam.cutetudee.presentation.model.TaskUi

data class TaskDetailsUiState(
    val task: TaskUi,
    val isLoading: Boolean = false,
)
