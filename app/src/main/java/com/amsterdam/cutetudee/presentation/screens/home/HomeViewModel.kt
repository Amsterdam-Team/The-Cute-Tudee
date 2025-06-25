package com.amsterdam.cutetudee.presentation.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.domain.model.Category
import com.amsterdam.cutetudee.domain.model.Task
import com.amsterdam.cutetudee.domain.service.AppSettingsService
import com.amsterdam.cutetudee.domain.service.CategoryService
import com.amsterdam.cutetudee.domain.service.TaskService
import com.amsterdam.cutetudee.presentation.model.TaskUi
import com.amsterdam.cutetudee.presentation.utils.IDateTimeHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
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
    private val appSettingsService: AppSettingsService,
) : ViewModel(),
    HomeScreenInteraction {
    private val _homeState = MutableStateFlow(HomeUiState())
    val homeState = _homeState.asStateFlow()

    private val _homeEffect = Channel<HomeEffect>(capacity = Channel.BUFFERED)
    val homeEffect = _homeEffect.receiveAsFlow()

    init {
        loadHomeScreenStates()
    }

    private fun loadHomeScreenStates() {
        tryToExecute {
            _homeState.update { it.copy(isLoading = true) }
            appSettingsService.isDarkMode().collectLatest { isDarkMode ->
                observeHomeStateChanges(isDarkMode)
            }
        }
    }

    private suspend fun observeHomeStateChanges(isDarkMode: Boolean) {
        val currentDate = getCurrentDate()
        val tasksFlow = taskService.getTasksByDate(currentDate)
        val categoriesFlow = categoryService.getAllCategories()

        combine(tasksFlow, categoriesFlow) { tasks, categories ->
            val homeState = createHomeUiState(tasks, categories)
            val moodState = determineMoodState(homeState)
            homeState.copy(moodState = moodState, isDarkMode = isDarkMode, isLoading = false)
        }.collect { finalState ->
            _homeState.update { finalState }
            Log.d("TAG", "observeHomeStateChanges: ${_homeState.value}")
        }
    }

    private fun getCurrentDate(): LocalDate =
        Clock.System
            .now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date

    private fun createHomeUiState(
        tasks: List<Task>,
        categories: List<Category>,
    ): HomeUiState = (tasks to categories).toHomeUiState(dateTimeHandler)

    private fun determineMoodState(state: HomeUiState): MoodState =
        when {
            state.totalTasksNumber == 0 -> MoodState.NOTHING_IN_YOUR_LIST
            state.doneTasksNumber == 0 -> MoodState.ZERO_PROGRESS
            state.totalTasksNumber == state.doneTasksNumber -> MoodState.TADAA
            else -> MoodState.STAY_WORKING
        }

    private fun tryToExecute(function: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                function()
            } catch (e: Exception) {
                _homeEffect.send(HomeEffect.ShowTaskEditedFailedSnackBar)
            }
        }
    }

    override fun onAddTaskClicked() {
        _homeState.update { it.copy(showAddTaskBottomSheet = true) }
    }

    override fun onDismissAddBottomSheet() {
        _homeState.update { it.copy(showAddTaskBottomSheet = false) }
    }

    override fun onTaskClicked(task: TaskUi) {
        _homeState.update { it.copy(showTaskDetailsBottomSheet = true, selectedTask = task) }
    }

    override fun onDismissTaskDetailsBottomSheet() {
        _homeState.update { it.copy(showTaskDetailsBottomSheet = false, selectedTask = null) }
    }

    override fun onEditTaskClicked() {
        _homeState.update {
            it.copy(
                showTaskDetailsBottomSheet = false,
                showEditTaskBottomSheet = true,
                selectedTask = it.selectedTask,
            )
        }
    }

    override fun onDismissEditBottomSheet() {
        _homeState.update {
            it.copy(showEditTaskBottomSheet = false, selectedTask = null)
        }
    }

    override fun onSwitchTheme() {
        val isDarkMode = !homeState.value.isDarkMode
        viewModelScope.launch {
            try {
                appSettingsService.setDarkMode(isDarkMode)
                _homeState.update { it.copy(isDarkMode = isDarkMode) }
            } catch (e: Exception) {
                _homeState.update { it.copy(errorMessageId = R.string.error_unknown) }
            }
        }
    }
}
