package com.amsterdam.cutetudee.presentation.screens.tasks

import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.lifecycle.viewModelScope
import com.amsterdam.cutetudee.domain.exception.NoTasksFoundPerDateException
import com.amsterdam.cutetudee.domain.service.CategoryService
import com.amsterdam.cutetudee.domain.service.TaskService
import com.amsterdam.cutetudee.presentation.base.BaseViewModel
import com.amsterdam.cutetudee.presentation.component.chip.tast_status.TaskStatusUi
import com.amsterdam.cutetudee.presentation.model.TaskUi
import com.amsterdam.cutetudee.presentation.model.toTaskUi
import com.amsterdam.cutetudee.presentation.utils.toBitmap
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

    fun getTasksByDate(date: LocalDate) {
        tryToExecute(
            function = { taskService.getTasksByDate(date) },
            onSuccess = { tasksFlow ->
                viewModelScope.launch {
                    tasksFlow.collect { tasks ->
                        val categoryIds = tasks.map { it.categoryId }.distinct()
                        val categoriesImages = categoryIds.map { categoryService.getCategoryById(it).image }

                        val tasksUi =
                            tasks.mapIndexed { index, task ->
                                val categoryImage = categoriesImages[index]
                                val imageBitmap = categoryImage.toBitmap().asImageBitmap()
                                val painter = BitmapPainter(imageBitmap)
                                task.toTaskUi(painter)
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
                // You could log this error instead of throwing if you want soft fail
                throw NoTasksFoundPerDateException(date)
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
