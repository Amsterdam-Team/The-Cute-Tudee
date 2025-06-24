package com.amsterdam.cutetudee.presentation.screens.tasks

sealed class TasksEffect {
    class ShowSuccessDeleteTaskSnackBar : TasksEffect()
    class ShowFailedSnackBar() : TasksEffect()

}