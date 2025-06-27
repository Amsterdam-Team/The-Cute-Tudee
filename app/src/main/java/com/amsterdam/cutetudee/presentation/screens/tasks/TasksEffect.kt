package com.amsterdam.cutetudee.presentation.screens.tasks

sealed class TasksEffect {
    data object ShowSuccessDeleteTaskSnackBar : TasksEffect()
    data object ShowFailedSnackBar : TasksEffect()
    data object ShowSuccessAddTaskSnackBar : TasksEffect()
    data object ShowSuccessEditTaskSnackBar : TasksEffect()
    data object ShowFailedAddTaskSnackBar : TasksEffect()
    data object ShowFailedEditTaskSnackBar : TasksEffect()
    data object ShowFailedWrongDateTaskSnackBar : TasksEffect()
}