package com.amsterdam.cutetudee.presentation.screens.home

import androidx.lifecycle.viewModelScope
import com.amsterdam.cutetudee.domain.service.CategoryService
import com.amsterdam.cutetudee.domain.service.TaskService
import com.amsterdam.cutetudee.presentation.base.BaseViewModel
import com.amsterdam.cutetudee.presentation.utils.IDateTimeHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class HomeViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService,
    private val dateTimeHandler: IDateTimeHandler
) : BaseViewModel<Unit>(Unit) {

    private val _homeState = MutableStateFlow(HomeUiState())
    val homeState = _homeState.asStateFlow()

    init {
        tryToExecute(
            function = {
                _homeState.update { it.copy(isLoading = true) }
                getCurrentDayTasks()
            },
            onSuccess = {},
            onError = { errorMessageId ->
                _homeState.update { it.copy(errorMessageId = errorMessageId, isLoading = false) }
            })
    }

    private fun getCurrentDayTasks() {
        val currentDate: LocalDate = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date
        val tasksFlow = taskService.getTasksByDate(currentDate)
        val categoriesFlow = categoryService.getAllCategories()
        viewModelScope.launch {
            combine(tasksFlow, categoriesFlow) { tasks, categories ->
                (tasks to categories).toHomeUiState(dateTimeHandler)
            }.collect { combinedData ->
                _homeState.update { combinedData }
            }
        }
    }
}