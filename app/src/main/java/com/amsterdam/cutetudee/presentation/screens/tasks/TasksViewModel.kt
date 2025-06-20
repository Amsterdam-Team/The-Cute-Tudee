package com.amsterdam.cutetudee.presentation.screens.tasks

import androidx.lifecycle.viewModelScope
import com.amsterdam.cutetudee.domain.model.Task
import com.amsterdam.cutetudee.domain.service.CategoryService
import com.amsterdam.cutetudee.domain.service.TaskService
import com.amsterdam.cutetudee.presentation.base.BaseViewModel
import com.amsterdam.cutetudee.presentation.component.chip.priority.toTaskPriority
import com.amsterdam.cutetudee.presentation.component.chip.tast_status.TaskStatusUi
import com.amsterdam.cutetudee.presentation.model.TaskUi
import com.amsterdam.cutetudee.presentation.model.toCategoryUi
import com.amsterdam.cutetudee.presentation.model.toTaskUi
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
class TasksViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService,
) : BaseViewModel<TasksUiState>(TasksUiState()) {
    init {
        getTasksByDate(_state.value.currentDate)
    }

    fun updateTaskStatusToDone(
        taskUi: TaskUi,
        onSuccess: () -> Unit,
    ) {
        tryToExecute(
            function = {
                taskService.editTask(
                    Task(
                        id = taskUi.id,
                        title = taskUi.title,
                        description = taskUi.description,
                        targetDate = taskUi.date,
                        priority = taskUi.priority.toTaskPriority(),
                        status = Task.Status.DONE,
                        categoryId = taskUi.categoryUi.id,
                    ),
                )
            },
            onSuccess = { onSuccess() },
            onError = {},
        )
    }

    fun deleteTask(
        taskUi: TaskUi,
        onDeletedSuccessfully: () -> Unit,
    ) {
        tryToExecute(
            function = { taskService.deleteTask(taskUi.id) },
            onSuccess = { onDeletedSuccessfully() },
            onError = {
            },
        )
    }

    fun onShowTaskDetails(taskUi: TaskUi) {
        _state.update { it.copy(showTaskDetailsBottomSheet = true, taskDetails = taskUi) }
    }

    fun onDismissTaskDetails() {
        _state.update { it.copy(showTaskDetailsBottomSheet = false, taskDetails = null) }
    }

    fun onFabAction() {
        _state.update { it.copy(showAddTaskBottomSheet = true) }
    }

    fun onDismissFabButton() {
        _state.update { it.copy(showAddTaskBottomSheet = false) }
    }

    fun getTasksByDate(date: LocalDate) {
        tryToExecute(
            function = { taskService.getTasksByDate(date) },
            onSuccess = { tasksFlow ->
                viewModelScope.launch {
                    tasksFlow.collect { tasks ->
                        val categoryIds = tasks.map { it.categoryId }.distinct()
                        val categoriesImages = categoryIds.map { categoryService.getCategoryById(it).toCategoryUi() }

                        val tasksUi =
                            tasks.mapIndexed { index, task ->
                                val categoryUi = categoriesImages[index]
                                task.toTaskUi(categoryUi)
                            }

                        val filteredTasksByStatus =
                            tasksUi.filter {
                                it.status == _state.value.currentSelectedTaskStatusUi
                            }

                        _state.update {
                            it.copy(
                                tasks = tasksUi,
                                filteredTasks = filteredTasksByStatus,
                                currentDate = date,
                            )
                        }
                    }
                }
            },
            onError = {
            },
        )
    }

    fun filteredTasksByStatus(selectedStatus: TaskStatusUi) {
        val tasks = _state.value.tasks
        val filteredTasks =
            tasks.filter {
                it.status.name == selectedStatus.name
            }
        _state.update {
            it.copy(filteredTasks = filteredTasks, currentSelectedTaskStatusUi = selectedStatus)
        }
    }

    fun navigateToNextMonth() {
        _state
            .update {
                val currentDate = it.currentDate
                val updatedDate = currentDate.plus(DatePeriod(months = 1))

                it.copy(
                    currentDate = updatedDate,
                )
            }.also {
                getTasksByDate(_state.value.currentDate)
            }
    }

    fun navigateToPreviousMonth() {
        _state
            .update {
                val currentDate = it.currentDate
                val updatedDate = currentDate.minus(DatePeriod(months = 1))
                it.copy(
                    currentDate = updatedDate,
                )
            }.also {
                getTasksByDate(_state.value.currentDate)
            }
    }
}
