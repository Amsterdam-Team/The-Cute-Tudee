package com.amsterdam.cutetudee.presentation.bottomSheets.taskDetails

import com.amsterdam.cutetudee.domain.model.Task

data class TaskDetailsUiState(
    val task: Task, // This need to be a TaskUi
    val isLoading: Boolean = false,
)
