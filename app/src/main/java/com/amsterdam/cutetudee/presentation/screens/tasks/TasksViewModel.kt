package com.amsterdam.cutetudee.presentation.screens.tasks

import androidx.lifecycle.viewModelScope
import com.amsterdam.cutetudee.domain.exception.NoTasksFoundPerDateException
import com.amsterdam.cutetudee.domain.repository.TaskService
import com.amsterdam.cutetudee.presentation.base.BaseViewModel
import com.amsterdam.cutetudee.presentation.component.chip.tast_status.TaskStatusUi
import com.amsterdam.cutetudee.presentation.component.chip.tast_status.toTaskStatusUi
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class TasksViewModel(
    private val taskService: TaskService,
) : BaseViewModel<TasksUiState>(TasksUiState()) {
    init {
        getTasksByDate(_state.value.currentDate)
    }

    fun getTasksByDate(date: LocalDate) {
        tryToExecute(
            function = { taskService.getTasksByDate(date) },
            onSuccess = { tasksFlow ->
                viewModelScope.launch {
                    tasksFlow.collect { tasks ->
                        val filteredTasksByStatus =
                            tasks.filter {
                                it.status.toTaskStatusUi() == _state.value.currentSelectedTaskStatusUi
                            }
                        _state.update {
                            it.copy(
                                tasks = tasks,
                                filteredTasks = filteredTasksByStatus,
                            )
                        }
                    }
                }
            },
            onError = {
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
}
