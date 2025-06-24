package com.amsterdam.cutetudee.presentation.screens.home

import android.net.Uri
import androidx.core.net.toUri
import com.amsterdam.cutetudee.domain.model.Category
import com.amsterdam.cutetudee.domain.model.Task
import com.amsterdam.cutetudee.domain.model.Task.Priority
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import com.amsterdam.cutetudee.presentation.utils.IDateTimeHandler
import kotlin.uuid.ExperimentalUuidApi


@OptIn(ExperimentalUuidApi::class)
fun Pair<List<Task>, List<Category>>.toHomeUiState(dateTimeHandler: IDateTimeHandler): HomeUiState {

    val (tasks, categories) = this

    fun Task.toTaskDetails(): HomeUiState.TaskDetails {
        val category = categories.find { it.id == categoryId }
        return HomeUiState.TaskDetails(
            icon = category?.image?.toUri() ?: Uri.EMPTY,
            title = title,
            description = description.orEmpty(),
            taskState = status,
            taskPriority = priority.toUi()
        )
    }

    val toDoTasks = tasks.filter { it.status == Task.Status.TODO }.map { it.toTaskDetails() }
    val inProgressTasks =
        tasks.filter { it.status == Task.Status.IN_PROGRESS }.map { it.toTaskDetails() }
    val doneTasks = tasks.filter { it.status == Task.Status.DONE }.map { it.toTaskDetails() }
    val formattedDate = dateTimeHandler.getStringDateFromMillis(
        dateTimeHandler.getCurrentDateInMillis(),
        "dd MMM yyyy"
    )
    return HomeUiState(
        currentDate = formattedDate,
        todoTasks = toDoTasks,
        inProgressTasks = inProgressTasks,
        doneTasks = doneTasks,
        toDoTasksNumber = toDoTasks.size,
        inProgressTasksNumber = inProgressTasks.size,
        doneTasksNumber = doneTasks.size,
        totalTasksNumber = tasks.size,
        isLoading = false,
        errorMessageId = null
    )
}

fun Priority.toUi(): PriorityUi = when (this) {
    Priority.LOW -> PriorityUi.LOW
    Priority.MEDIUM -> PriorityUi.MEDIUM
    Priority.HIGH -> PriorityUi.HIGH
}