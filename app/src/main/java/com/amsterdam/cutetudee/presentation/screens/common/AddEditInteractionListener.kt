package com.amsterdam.cutetudee.presentation.screens.common

import com.amsterdam.cutetudee.domain.entity.Task

interface AddEditTaskInteractionListener {
    fun onTaskNameChanged(updatedTaskName: String)
    fun onTaskDescriptionChanged(updatedTaskDescription: String)
    fun onPriorityChanged(priority: Task.Priority)
    fun onDateChanged(date: Long)
    fun onCategorySelected(categoryId: String)
    fun onAction()
    fun onCancel()
    fun onDismiss()
}