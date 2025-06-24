package com.amsterdam.cutetudee.presentation.screens.tasks

import com.amsterdam.cutetudee.presentation.component.chip.tast_status.TaskStatusUi
import com.amsterdam.cutetudee.presentation.model.TaskUi

interface TasksInteraction {
    fun onFabButtonClicked()
    fun onDismissFabButton()
    fun onTabChange(taskStatusUi: TaskStatusUi)
    fun onUpdateSelectedDate(dateInMillis: Long)
    fun onNextMonthClicked()
    fun onPreviousMonthClicked()
    fun onClickDateDialogButton()
    fun onDismissDateDialogButton()
    fun onDeleteTaskClicked(taskUi: TaskUi)
    fun onConfirmDeletedTheTask()
    fun onDismissDeleteBottomSheet()
    fun onChangeTaskStatusToDoneClicked(taskUi: TaskUi)
    fun onSelectedDayChange(dayNumber : Int)
    fun onTaskClicked(task : TaskUi)
    fun onDismissDetailsBottomSheet()
    fun onEditTaskClicked()
    fun onDismissEditBottomSheet()
}