package com.amsterdam.cutetudee.presentation.screens.tasks

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.amsterdam.cutetudee.domain.entity.Category
import com.amsterdam.cutetudee.domain.entity.Task
import com.amsterdam.cutetudee.domain.service.CategoryService
import com.amsterdam.cutetudee.domain.service.TaskService
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import com.amsterdam.cutetudee.presentation.component.chip.priority.toPriorityUi
import com.amsterdam.cutetudee.presentation.component.chip.tast_status.TaskStatusUi
import com.amsterdam.cutetudee.presentation.component.chip.tast_status.toTaskStatus
import com.amsterdam.cutetudee.presentation.model.TaskUi
import com.amsterdam.cutetudee.presentation.model.toCategoryUi
import com.amsterdam.cutetudee.presentation.model.toTask
import com.amsterdam.cutetudee.presentation.model.toTaskUi
import com.amsterdam.cutetudee.presentation.navigation.Screen
import com.amsterdam.cutetudee.presentation.screens.common.AddEditTaskInteractionListener
import com.amsterdam.cutetudee.presentation.screens.common.AddEditTaskUiState
import com.amsterdam.cutetudee.presentation.screens.common.toAddEditCategoryUiState
import com.amsterdam.cutetudee.presentation.screens.common.toTask
import com.amsterdam.cutetudee.presentation.utils.dispatcher.DispatcherProvider
import com.amsterdam.cutetudee.presentation.utils.getLocalDateFromMillis
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
class TasksViewModel(
    savedStateHandle: SavedStateHandle,
    private val taskService: TaskService,
    private val categoryService: CategoryService,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel(),
    TasksInteraction, AddEditTaskInteractionListener {
    private val _taskUiState = MutableStateFlow(TasksUiState())
    val taskUiState = _taskUiState.asStateFlow()

    private val _effect = MutableSharedFlow<TasksEffect>()
    val effect = _effect.asSharedFlow()

    init {
        val argument = savedStateHandle.toRoute<Screen.Tasks>()
        if (argument.status != null) {
            _taskUiState.update { it.copy(currentSelectedTaskStatusUi = argument.status) }
        }
        loadTasksForDate(_taskUiState.value.currentDate)
    }

    override fun onDismissFabButton() {
        _taskUiState.update { it.copy(isAddTaskBottomSheetVisible = false) }
    }

    override fun onFabButtonClicked() {
        if (_taskUiState.value.isSelectedDateBeforeCurrentDate < 0) {
            tryToExecute {
                _effect.emit(TasksEffect.ShowFailedWrongDateTaskSnackBar)
            }
            return
        }
        if (_taskUiState.value.addEditTaskUiState.categories.isEmpty()) {
            loadCategories()
        }
        _taskUiState.update {
            it.copy(
                isAddTaskBottomSheetVisible = true,
                addEditTaskUiState =
                    it.addEditTaskUiState.copy(
                        date = taskUiState.value.currentDate,
                    ),
            )
        }
    }

    override fun onTabChange(taskStatusUi: TaskStatusUi) {
        _taskUiState.update { it.copy(currentSelectedTaskStatusUi = taskStatusUi) }
        loadTasksForDate(_taskUiState.value.currentDate)
    }

    override fun onUpdateSelectedDate(dateInMillis: Long) {
        val updatedDate = dateInMillis.getLocalDateFromMillis()
        loadTasksForDate(updatedDate)
    }

    override fun onNextMonthClicked() {
        val updatedDate = _taskUiState.value.currentDate.plus(DatePeriod(months = 1))
        loadTasksForDate(updatedDate)
    }

    override fun onPreviousMonthClicked() {
        val updatedDate = _taskUiState.value.currentDate.minus(DatePeriod(months = 1))
        loadTasksForDate(updatedDate)
    }

    override fun onClickDateDialogButton() {
        _taskUiState.update { it.copy(isDateDialogVisible = true) }
    }

    override fun onDismissDateDialogButton() {
        _taskUiState.update { it.copy(isDateDialogVisible = false) }
    }

    override fun onDeleteTaskClicked(taskUi: TaskUi) {
        _taskUiState.update {
            it.copy(
                selectedDeleteTaskId = taskUi.id,
                isDeleteBottomSheetVisible = true,
            )
        }
    }

    override fun onConfirmDeletedTheTask() {
        tryToExecute {
            taskService.deleteTask(_taskUiState.value.selectedDeleteTaskId!!)
            loadTasksForDate(_taskUiState.value.currentDate)
            _effect.emit(TasksEffect.ShowSuccessDeleteTaskSnackBar)
            _taskUiState.update {
                it.copy(
                    selectedDeleteTaskId = null,
                    isDeleteBottomSheetVisible = false
                )
            }
        }
    }

    override fun onDismissDeleteBottomSheet() {
        _taskUiState.update {
            it.copy(
                selectedDeleteTaskId = null,
                isDeleteBottomSheetVisible = false
            )
        }
    }

    override fun onMoveToNextStatus(taskStatusUi: TaskStatusUi) {
        tryToExecute {
            taskService.editTask(
                _taskUiState.value.selectedTask!!
                    .toTask()
                    .copy(status = taskStatusUi.toTaskStatus()),
            )
            _taskUiState.update { it.copy(selectedTask = it.selectedTask!!.copy(status = taskStatusUi)) }
        }
    }

    override fun onSelectedDayChange(dayNumber: Int) {
        val currentDate = _taskUiState.value.currentDate
        val todayDate = Clock.System
            .now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date
        val updatedDate =
            LocalDate(
                year = currentDate.year,
                month = currentDate.month,
                dayOfMonth = dayNumber,
            )
        _taskUiState.update {
            it.copy(
                selectedDate = updatedDate,
                isSelectedDateBeforeCurrentDate = updatedDate.compareTo(todayDate)
            )
        }
        loadTasksForDate(updatedDate)
    }

    override fun onTaskClicked(task: TaskUi) {
        _taskUiState.update { it.copy(isDetailsBottomSheetVisible = true, selectedTask = task) }
    }

    override fun onDismissDetailsBottomSheet() {
        _taskUiState.update { it.copy(isDetailsBottomSheetVisible = false) }
    }

    override fun onEditTaskClicked(
        id: String,
        name: String,
        description: String,
        date: LocalDate,
        priority: PriorityUi,
        selectedCategoryId: String
    ) {
        loadCategories()
        _taskUiState.update {
            it.copy(
                isEditBottomSheetVisible = true,
                isDetailsBottomSheetVisible = false,
                addEditTaskUiState = AddEditTaskUiState(
                    id = id,
                    taskName = name,
                    description = description,
                    date = date,
                    priority = priority,
                    selectedCategoryId = selectedCategoryId,
                    categories = taskUiState.value.addEditTaskUiState.categories,
                    taskAction = AddEditTaskUiState.TaskAction.EDIT,
                    status = taskUiState.value.selectedTask?.status?.toTaskStatus()
                        ?: Task.Status.TODO
                )
            )
        }
    }

    override fun onDismissEditBottomSheet() {
        _taskUiState.update { it.copy(isEditBottomSheetVisible = false) }
    }

    private fun loadTasksForDate(date: LocalDate) {
        tryToExecute {
            getTasksByDate(date).collect { tasks ->
                _taskUiState.update { currentState ->
                    currentState.copy(
                        currentDate = date,
                        tasks = tasks.filter { it.status == currentState.currentSelectedTaskStatusUi },
                    )
                }
            }
        }
    }

    fun getTasksByDate(date: LocalDate): Flow<List<TaskUi>> {
        val tasksFlow = taskService.getTasksByDate(date)
        val categoriesFlow = categoryService.getAllCategories()
        return combine(tasksFlow, categoriesFlow) { tasks, categories ->
            tasks.map { task ->
                val category = categories.first { it.id == task.categoryId }
                task.toTaskUi(category.toCategoryUi())
            }
        }
    }

    private fun tryToExecute(function: suspend () -> Unit) {
        viewModelScope.launch(dispatcherProvider.IO) {
            try {
                function()
            } catch (_: Exception) {
                _effect.emit(TasksEffect.ShowFailedSnackBar)
            }
        }
    }

    override fun onTaskNameChanged(updatedTaskName: String) {
        _taskUiState.update { state ->
            state.copy(
                addEditTaskUiState = state.addEditTaskUiState.copy(
                    taskName = updatedTaskName
                )
            )
        }
        checkIfTaskDataValid()
    }

    override fun onTaskDescriptionChanged(updatedTaskDescription: String) {
        _taskUiState.update { state ->
            state.copy(
                addEditTaskUiState = state.addEditTaskUiState.copy(
                    description = updatedTaskDescription
                )
            )
        }
        checkIfTaskDataValid()
    }

    override fun onPriorityChanged(priority: Task.Priority) {
        _taskUiState.update { state ->
            state.copy(
                addEditTaskUiState = state.addEditTaskUiState.copy(
                    priority = priority.toPriorityUi()
                )
            )
        }
        checkIfTaskDataValid()
    }

    override fun onDateChanged(date: Long) {
        _taskUiState.update { state ->
            state.copy(
                addEditTaskUiState = state.addEditTaskUiState.copy(
                    date = date.getLocalDateFromMillis()
                )
            )
        }
        checkIfTaskDataValid()
    }

    override fun onCategorySelected(categoryId: String) {
        _taskUiState.update { state ->
            state.copy(
                addEditTaskUiState = state.addEditTaskUiState.copy(
                    selectedCategoryId = categoryId
                )
            )
        }
        checkIfTaskDataValid()
    }

    override fun onAction() {
        updateIsLoading(true)
        when (_taskUiState.value.addEditTaskUiState.taskAction) {
            AddEditTaskUiState.TaskAction.ADD -> addTask()
            AddEditTaskUiState.TaskAction.EDIT -> editTask()
        }
        onDismiss()
    }

    override fun onCancel() {
        _taskUiState.update {
            it.copy(
                addEditTaskUiState = AddEditTaskUiState(
                    categories = taskUiState.value.addEditTaskUiState.categories
                )
            )
        }
        onDismiss()
    }

    override fun onDismiss() {
        _taskUiState.update {
            it.copy(
                isAddTaskBottomSheetVisible = false,
                isEditBottomSheetVisible = false,
            )
        }
    }

    private fun updateIsLoading(isLoading: Boolean) {
        _taskUiState.update { state ->
            state.copy(
                addEditTaskUiState = state.addEditTaskUiState.copy(
                    isLoading = isLoading
                )
            )
        }
    }

    private fun enableAddEditTaskAction(isActionEnabled: Boolean) {
        _taskUiState.update { state ->
            state.copy(
                addEditTaskUiState = state.addEditTaskUiState.copy(
                    isEnabled = isActionEnabled
                )
            )
        }
    }

    private fun checkIfTaskDataValid() {
        val enableActionButton = when (taskUiState.value.addEditTaskUiState.taskAction) {
            AddEditTaskUiState.TaskAction.ADD -> isValidAddTaskData()
            AddEditTaskUiState.TaskAction.EDIT -> isValidEditTaskData()
        }
        enableAddEditTaskAction(enableActionButton) }

    @OptIn(ExperimentalUuidApi::class)
    private fun isValidEditTaskData(): Boolean {
        if (taskUiState.value.selectedTask == null) return false
        val isOldTaskName = taskUiState.value.selectedTask!!.title == taskUiState.value.addEditTaskUiState.taskName
        val isOldTaskCategory =
            taskUiState.value.selectedTask!!.categoryUi.id.toString() == taskUiState.value.addEditTaskUiState.selectedCategoryId
        val isOldTaskDescription =
            taskUiState.value.selectedTask!!.description == taskUiState.value.addEditTaskUiState.description
        val isOldTaskPriority =
            taskUiState.value.selectedTask!!.priority == taskUiState.value.addEditTaskUiState.priority
        val isOldTaskDate =
            taskUiState.value.selectedTask!!.date == taskUiState.value.addEditTaskUiState.date

        return (taskUiState.value.addEditTaskUiState.taskName.isNotBlank()
                && !(isOldTaskName && isOldTaskCategory && isOldTaskPriority && isOldTaskDescription && isOldTaskDate))
    }

    private fun isValidAddTaskData(): Boolean {
        return (taskUiState.value.addEditTaskUiState.taskName.isNotBlank()
                && taskUiState.value.addEditTaskUiState.selectedCategoryId.isNotBlank())
    }

    private fun editTask() {
        updateIsLoading(true)
        viewModelScope.launch(dispatcherProvider.IO) {
            try {
                taskService.editTask(
                    _taskUiState.value.addEditTaskUiState.toTask()
                )
                updateIsLoading(false)
                _effect.emit(TasksEffect.ShowSuccessEditTaskSnackBar)
            } catch (_: Exception) {
                updateIsLoading(false)
                _effect.emit(TasksEffect.ShowFailedEditTaskSnackBar)
            }
        }
    }

    private fun addTask() {
        updateIsLoading(true)
        viewModelScope.launch(dispatcherProvider.IO) {
            try {
                taskService.addTask(
                    _taskUiState.value.addEditTaskUiState.toTask()
                )
                updateIsLoading(false)
                onDismiss()
                _effect.emit(TasksEffect.ShowSuccessAddTaskSnackBar)
            } catch (_: Exception) {
                updateIsLoading(false)
                _effect.emit(TasksEffect.ShowFailedAddTaskSnackBar)
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch(dispatcherProvider.IO) {
            try {
                categoryService.getAllCategories()
                    .collectLatest { categories ->
                        updateUiState(
                            categories
                        )
                    }
            } catch (_: Exception) {

            }
        }
    }

    private fun updateUiState(
        categories: List<Category>
    ) {
        _taskUiState.update { state ->
            val convertedCategories =
                categories.map { category -> category.toAddEditCategoryUiState() }
            state.copy(
                addEditTaskUiState = state.addEditTaskUiState.copy(
                    categories = convertedCategories
                ),
            )
        }
    }
}
