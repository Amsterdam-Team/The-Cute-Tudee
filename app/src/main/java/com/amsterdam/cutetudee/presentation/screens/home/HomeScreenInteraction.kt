package com.amsterdam.cutetudee.presentation.screens.home

import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import com.amsterdam.cutetudee.presentation.component.chip.tast_status.TaskStatusUi
import kotlinx.datetime.LocalDate

interface HomeScreenInteraction {
    fun onAddTaskClicked()

    fun onDismissAddBottomSheet()

    fun onTaskClicked(taskId: String)

    fun onDismissTaskDetailsBottomSheet()

    fun onEditTaskClicked(
            id: String,
            name: String,
            description: String,
            date: LocalDate,
            priority: PriorityUi,
            selectedCategoryId: String
    )

    fun onDismissEditBottomSheet()

    fun onMoveToNextStatus(taskStatusUi: TaskStatusUi)

    fun onSwitchTheme()
}
