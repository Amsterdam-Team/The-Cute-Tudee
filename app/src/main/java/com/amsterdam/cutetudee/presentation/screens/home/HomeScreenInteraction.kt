package com.amsterdam.cutetudee.presentation.screens.home

import com.amsterdam.cutetudee.presentation.model.TaskUi

interface HomeScreenInteraction {
    fun onAddTaskClicked()

    fun onDismissAddBottomSheet()

    fun onTaskClicked(task: TaskUi)

    fun onDismissTaskDetailsBottomSheet()

    fun onEditTaskClicked()

    fun onDismissEditBottomSheet()

    fun onSwitchTheme()
}
