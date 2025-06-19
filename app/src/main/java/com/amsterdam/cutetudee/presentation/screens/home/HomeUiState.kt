package com.amsterdam.cutetudee.presentation.screens.home

import androidx.annotation.DrawableRes
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.domain.model.Task

data class HomeUiState(
    val inProgressTasks: List<Task> = emptyList(),
    val todoTasks: List<Task> = emptyList(),
    val doneTasks: List<Task> = emptyList(),
    val sliderState: MoodState = MoodState.STAY_WORKING,
    val taskDetailsState: TaskDetailsState = TaskDetailsState(),
    val taskBottomSheetState: TaskBottomSheetState = TaskBottomSheetState(),
    val isDark: Boolean = false,
    val addNewTask: Boolean = false,
    val editTask: Boolean = false,
    val taskDetails: Boolean = false,
) {

    data class TaskDetailsState(
        @DrawableRes val icon: Int = R.drawable.quran_icon,
        val title: String = "",
        val description: String = "",
        val taskState: TaskState = TaskState.TODO,
        val taskPriority: Task.Priority = Task.Priority.LOW,
    )

    data class TaskBottomSheetState(
        val taskId: Long = -1,
        val title: String = "",
        val description: String = "",
        val date: String = "",
        val priority: Task.Priority = Task.Priority.LOW,
        val categoryId: Long = -1L,
        val isEditMode: Boolean = false
    )

    enum class TaskState {
        TODO,
        IN_PROGRESS,
        DONE,
    }

    enum class MoodState {
        STAY_WORKING,
        TADOO,
        ZERO_PROGRESS,
        NOTHING_IN_YOUR_LIST
    }
}