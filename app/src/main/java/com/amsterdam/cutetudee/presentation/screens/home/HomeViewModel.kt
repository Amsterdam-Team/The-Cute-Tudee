package com.amsterdam.cutetudee.presentation.screens.home

import androidx.lifecycle.viewModelScope
import com.amsterdam.cutetudee.domain.exception.NoCategoriesFoundException
import com.amsterdam.cutetudee.domain.repository.CategoryService
import com.amsterdam.cutetudee.domain.repository.TaskService
import com.amsterdam.cutetudee.presentation.base.BaseViewModel
import com.amsterdam.cutetudee.presentation.utils.IDateTimeHandler
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

    init {
        tryToExecute(
        function = {getCurrentDayTasks()},
         onSuccess = {},
            onError = {

            })
    }

    private fun getCurrentDayTasks() {
        val currentDate: LocalDate = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date
        val tasksFlow = taskService.getTasksByDate(currentDate)
        val categoriesFlow = categoryService.getAllCategories()
            throw NoCategoriesFoundException()
        viewModelScope.launch {
            combine(tasksFlow, categoriesFlow) { tasks, categories ->
                (tasks to categories).toHomeUiState(dateTimeHandler)
            }.collect { combinedData ->
                _state.update {combinedData}
            }
        }
    }
}