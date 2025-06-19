package com.amsterdam.cutetudee.presentation.screens.tasks

import androidx.lifecycle.viewModelScope
import com.amsterdam.cutetudee.domain.exception.NoTasksFoundPerDateException
import com.amsterdam.cutetudee.domain.repository.TaskService
import com.amsterdam.cutetudee.presentation.base.BaseViewModel
import com.amsterdam.cutetudee.presentation.component.chip.tast_status.TaskStatusUi
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class TasksViewModel(
    private val taskService: TaskService
) : BaseViewModel<TasksUiState>(TasksUiState()) {
    init {
        getTasksByDate(_state.value.currentDate)
    }

    fun getTasksByDate(date: LocalDate) {
        tryToExecute({ taskService.getTasksByDate(date) }, onSuccess = { tasksFlow ->
            viewModelScope.launch {
                tasksFlow.collect { tasks ->
                    _state.update { it.copy(tasks = tasks) }
                }
            }
        }, onError = {
            NoTasksFoundPerDateException(date)
        }
        )
    }


    fun filteredTasksByStatus(selectedStatus: TaskStatusUi) {
        val tasks = _state.value.tasks
        val filteredTasks = tasks.filter {
            it.status.name == selectedStatus.name
        }
        _state.update {
            it.copy(tasks=filteredTasks)
        }
    }
}