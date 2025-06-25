package com.amsterdam.cutetudee.presentation.screens.tasks

sealed class TasksEffect {
    class ShowSuccessDeleteTaskSnackBar : TasksEffect()
    class ShowFailedSnackBar() : TasksEffect()
    class ShowSuccessAddTaskSnackBar: TasksEffect()
    class ShowSuccessEditTaskSnackBar: TasksEffect()
    class ShowFailedAddTaskSnackBar: TasksEffect()
    class ShowFailedEditTaskSnackBar: TasksEffect()
}