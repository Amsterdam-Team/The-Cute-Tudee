package com.amsterdam.cutetudee.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.domain.entity.Category
import com.amsterdam.cutetudee.domain.entity.Task
import com.amsterdam.cutetudee.domain.service.AppSettingsService
import com.amsterdam.cutetudee.domain.service.CategoryService
import com.amsterdam.cutetudee.domain.service.TaskService
import com.amsterdam.cutetudee.domain.utils.ThemeMode
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import com.amsterdam.cutetudee.presentation.component.chip.priority.toPriorityUi
import com.amsterdam.cutetudee.presentation.component.chip.tast_status.TaskStatusUi
import com.amsterdam.cutetudee.presentation.component.chip.tast_status.toTaskStatus
import com.amsterdam.cutetudee.presentation.model.toCategoryUi
import com.amsterdam.cutetudee.presentation.model.toTask
import com.amsterdam.cutetudee.presentation.model.toTaskUi
import com.amsterdam.cutetudee.presentation.screens.categoryDetails.toUuid
import com.amsterdam.cutetudee.presentation.screens.common.AddEditTaskInteractionListener
import com.amsterdam.cutetudee.presentation.screens.common.AddEditTaskUiState
import com.amsterdam.cutetudee.presentation.screens.common.toAddEditCategoryUiState
import com.amsterdam.cutetudee.presentation.screens.common.toTask
import com.amsterdam.cutetudee.presentation.utils.getStringDateFromMillis
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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
    private val appSettingsService: AppSettingsService
) : ViewModel(),
    HomeScreenInteraction, AddEditTaskInteractionListener {
    private val _homeState = MutableStateFlow(HomeUiState())
    val homeState = _homeState.asStateFlow()

    private val _homeEffect = MutableSharedFlow<HomeEffect>()
    val homeEffect = _homeEffect.asSharedFlow()

    init {
        loadHomeScreenStates()
    }

    private fun loadHomeScreenStates() {
        tryToExecute {
            _homeState.update { it.copy(isLoading = true) }
            appSettingsService.getThemeMode().collectLatest { mode ->
                val isDarkMode = mode == ThemeMode.DARK
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
    ): HomeUiState = (tasks to categories).toHomeUiState()

    private fun determineMoodState(state: HomeUiState): MoodState =
        when {
            state.totalTasksNumber == 0 -> MoodState.NOTHING_IN_YOUR_LIST
            state.doneTasksNumber == 0 -> MoodState.ZERO_PROGRESS
            state.totalTasksNumber == state.doneTasksNumber -> MoodState.TADAA
            else -> MoodState.STAY_WORKING
        }

    private fun tryToExecute(function: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                function()
            } catch (e: Exception) {
                _homeEffect.emit(HomeEffect.ShowTaskEditedFailedSnackBar)
            }
        }
    }

    override fun onAddTaskClicked() {
        if (_homeState.value.addEditTaskUiState.categories.isEmpty()) {
            loadCategories()
        }
        _homeState.update { it.copy(showAddTaskBottomSheet = true) }
    }

    override fun onDismissAddBottomSheet() {
        _homeState.update { it.copy(showAddTaskBottomSheet = false) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun onTaskClicked(taskId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val task = taskService.getTaskById(taskId.toUuid())
                val category = categoryService.getCategoryById(task.categoryId)
                val mappedTask = task.toTaskUi(category.toCategoryUi())
                _homeState.update {
                    it.copy(
                        showTaskDetailsBottomSheet = true,
                        selectedTask = mappedTask
                    )
                }
            } catch (e: Exception) {
                _homeEffect.emit(HomeEffect.ShowFailedToLoadTaskSnackBar)
            }
        }
    }

    override fun onDismissTaskDetailsBottomSheet() {
        _homeState.update { it.copy(showTaskDetailsBottomSheet = false, selectedTask = null) }
    }

    override fun onEditTaskClicked() {
        if (_homeState.value.addEditTaskUiState.categories.isEmpty()) {
            loadCategories()
        }
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
        viewModelScope.launch(Dispatchers.IO) {
            try {
                appSettingsService.setThemeMode(isDarkMode.toThemeMode())
                _homeState.update { it.copy(isDarkMode = isDarkMode) }
            } catch (e: Exception) {
                _homeState.update { it.copy(errorMessageId = R.string.error_unknown) }
            }
        }
    }

    override fun onTaskNameChanged(updatedTaskName: String) {
        _homeState.update { state ->
            state.copy(
                addEditTaskUiState = state.addEditTaskUiState.copy(
                    taskName = updatedTaskName
                )
            )
        }
        checkIfDataFilled()
    }

    override fun onTaskDescriptionChanged(updatedTaskDescription: String) {
        _homeState.update { state ->
            state.copy(
                addEditTaskUiState = state.addEditTaskUiState.copy(
                    description = updatedTaskDescription
                )
            )
        }
        checkIfDataFilled()
    }

    override fun onPriorityChanged(priority: Task.Priority) {
        _homeState.update { state ->
            state.copy(
                addEditTaskUiState = state.addEditTaskUiState.copy(
                    priority = priority.toPriorityUi()
                )
            )
        }
        checkIfDataFilled()
    }

    override fun onDateChanged(date: Long) {
        _homeState.update { state ->
            state.copy(
                addEditTaskUiState = state.addEditTaskUiState.copy(
                    dateInMillis = date,
                    date = date.getStringDateFromMillis()
                )
            )
        }
        checkIfDataFilled()
    }

    override fun onCategorySelected(categoryId: String) {
        _homeState.update { state ->
            state.copy(
                addEditTaskUiState = state.addEditTaskUiState.copy(
                    selectedCategoryId = categoryId
                )
            )
        }
        checkIfDataFilled()
    }

    override fun onAction() {
        updateIsLoading(true)
        when (_homeState.value.addEditTaskUiState.taskAction) {
            AddEditTaskUiState.TaskAction.ADD -> addTask()
            AddEditTaskUiState.TaskAction.EDIT -> editTask()
        }
        onDismiss()
    }

    override fun onCancel() {
        _homeState.update {
            it.copy(
                addEditTaskUiState = AddEditTaskUiState(
                    categories = homeState.value.addEditTaskUiState.categories
                )
            )
        }
        onDismiss()
    }

    override fun onDismiss() {
        _homeState.update {
            it.copy(
                showAddTaskBottomSheet = false,
                showEditTaskBottomSheet = false
            )
        }
    }

    private fun updateIsLoading(isLoading: Boolean) {
        _homeState.update { state ->
            state.copy(
                addEditTaskUiState = state.addEditTaskUiState.copy(
                    isLoading = isLoading
                )
            )
        }
    }

    private fun updateIsDataFilled(isDataFilled: Boolean) {
        _homeState.update { state ->
            state.copy(
                addEditTaskUiState = state.addEditTaskUiState.copy(
                    isEnabled = isDataFilled
                )
            )
        }
    }

    private fun checkIfDataFilled() {
        if (homeState.value.addEditTaskUiState.taskName.isNotBlank()
            && homeState.value.addEditTaskUiState.description.isNotBlank()
            && homeState.value.addEditTaskUiState.selectedCategoryId.isNotBlank()
        ) {
            updateIsDataFilled(true)
        } else {
            updateIsDataFilled(false)
        }
    }

    private fun editTask() {
        updateIsLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                taskService.editTask(
                    _homeState.value.addEditTaskUiState.toTask()
                )
                updateIsLoading(false)
                _homeEffect.emit(HomeEffect.ShowTaskEditedSuccessfullySnackBar)
            } catch (_: Exception) {
                updateIsLoading(false)
                _homeEffect.emit(HomeEffect.ShowTaskEditedFailedSnackBar)
            }
        }
    }

    private fun addTask() {
        updateIsLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                taskService.addTask(
                    _homeState.value.addEditTaskUiState.toTask()
                )
                updateIsLoading(false)
                onDismiss()
                _homeEffect.emit(HomeEffect.ShowTaskAddedSuccessfullySnackBar)
            } catch (_: Exception) {
                updateIsLoading(false)
                _homeEffect.emit(HomeEffect.ShowTaskAddedFailedSnackBar)
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                categoryService.getAllCategories()
                    .collectLatest { categories ->
                        updateUiState(
                            categories,
                            AddEditTaskUiState.TaskAction.ADD
                        )
                    }
            } catch (e: Exception) {
            }
        }
    }

    private fun updateUiState(
        categories: List<Category>,
        taskAction: AddEditTaskUiState.TaskAction
    ) {
        _homeState.update { state ->
            val convertedCategories =
                categories.map { category -> category.toAddEditCategoryUiState() }
            state.copy(
                addEditTaskUiState = state.addEditTaskUiState.copy(
                    categories = convertedCategories,
                    taskAction = taskAction
                ),
            )
        }
    }
}
