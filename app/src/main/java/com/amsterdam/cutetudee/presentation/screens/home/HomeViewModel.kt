package com.amsterdam.cutetudee.presentation.screens.home

import androidx.lifecycle.viewModelScope
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.domain.service.CategoryService
import com.amsterdam.cutetudee.domain.service.TaskService
import com.amsterdam.cutetudee.presentation.base.BaseViewModel
import com.amsterdam.cutetudee.presentation.utils.IDateTimeHandler
import com.amsterdam.cutetudee.presentation.utils.ThemeManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class HomeViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService,
    private val dateTimeHandler: IDateTimeHandler,
    private val themeManager: ThemeManager
) : BaseViewModel<Unit>(Unit) {

    private val _homeState = MutableStateFlow(HomeUiState())
    val homeState = _homeState.asStateFlow()

    init {
        tryToExecute(
            function = {
                _homeState.update { it.copy(isLoading = true) }
                observeHomeStateChanges()
                getCurrentTheme()
            },
            onSuccess = {},
            onError = { errorMessageId ->
                _homeState.update { it.copy(errorMessageId = errorMessageId, isLoading = false) }
            })
    }


    fun onToggledAction() {
        val isDarkMode = !homeState.value.isDarkMode
        viewModelScope.launch {
            try {
                themeManager.updateTheme(
                    isDarkMode
                )
                _homeState.update { it.copy(isDarkMode = isDarkMode) }
            } catch (e: Exception) {
                _homeState.update { it.copy(errorMessageId = R.string.error_unknown) }
            }
        }
    }

    private fun getCurrentTheme() {
        viewModelScope.launch {
            themeManager.initialize()
            themeManager.themeFlow.filterNotNull().distinctUntilChanged().collect { theme ->
                _homeState.update { it.copy(isDarkMode = theme) }
            }
        }
    }

    private fun observeHomeStateChanges() {
        val currentDate: LocalDate = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date
        val tasksFlow = taskService.getTasksByDate(currentDate)
        val categoriesFlow = categoryService.getAllCategories()

        viewModelScope.launch {
            combine(tasksFlow, categoriesFlow) { tasks, categories ->
                val currentState = (tasks to categories).toHomeUiState(dateTimeHandler)

                val moodState = when {
                    currentState.doneTasksNumber == 0 -> MoodState.ZERO_PROGRESS
                    currentState.totalTasksNumber == 0 -> MoodState.NOTHING_IN_YOUR_LIST
                    currentState.totalTasksNumber == currentState.doneTasksNumber -> MoodState.TADOO
                    else -> MoodState.STAY_WORKING
                }

                currentState.copy(moodState = moodState)

            }.collect { finalState ->
                _homeState.update { finalState }
            }
        }
    }
}