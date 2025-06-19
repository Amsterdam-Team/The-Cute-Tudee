package com.amsterdam.cutetudee.presentation.screens.home

import androidx.annotation.StringRes
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.domain.model.Task
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi

data class HomeUiState(
    val currentDate : String = "",
    val inProgressTasks: List<TaskDetails> = emptyList(),
    val todoTasks: List<TaskDetails> = emptyList(),
    val doneTasks: List<TaskDetails> = emptyList(),
    val toDoTasksNumber : Int = 0,
    val inProgressTasksNumber : Int = 0,
    val doneTasksNumber : Int = 0,
    val totalTasksNumber : Int = 0,
    val isLoading :Boolean = false,
   @StringRes val errorMessageId : Int? = null
) {
    data class TaskDetails(
        val icon: String = "",
        val title: String = "",
        val description: String = "",
        val taskState: Task.Status = Task.Status.TODO,
        val taskPriority: PriorityUi = PriorityUi.LOW,
    )
}


//data class TaskBottomSheetState(
//    val taskId: Long = -1,
//    val title: String = "",
//    val description: String = "",
//    val date: String = "",
//    val priority: Task.Priority = Task.Priority.LOW,
//    val categoryId: Long = -1L,
//    val isEditMode: Boolean = false
//)
//
//
//enum class MoodState {
//    STAY_WORKING,
//    TADOO,
//    ZERO_PROGRESS,
//    NOTHING_IN_YOUR_LIST
//}