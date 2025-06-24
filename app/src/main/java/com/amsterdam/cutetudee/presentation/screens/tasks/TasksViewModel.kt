package com.amsterdam.cutetudee.presentation.screens.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amsterdam.cutetudee.domain.model.Task
import com.amsterdam.cutetudee.domain.service.CategoryService
import com.amsterdam.cutetudee.domain.service.TaskService
import com.amsterdam.cutetudee.presentation.component.chip.tast_status.TaskStatusUi
import com.amsterdam.cutetudee.presentation.model.TaskUi
import com.amsterdam.cutetudee.presentation.model.toCategoryUi
import com.amsterdam.cutetudee.presentation.model.toTask
import com.amsterdam.cutetudee.presentation.model.toTaskUi
import com.amsterdam.cutetudee.presentation.utils.IDateTimeHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
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
    private val dateTimeHandler: IDateTimeHandler
) : ViewModel(), TasksInteraction {

    private val _state = MutableStateFlow(TasksUiState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<TasksEffect>()
    val effect = _effect.asSharedFlow()

    init {
        loadTasksForDate(_state.value.currentDate)
    }

    override fun onDismissFabButton() {
        _state.update { it.copy(isAddTaskBottomSheetVisible = false) }
    }

    override fun onFabButtonClicked() {
        _state.update { it.copy(isAddTaskBottomSheetVisible = true) }
    }

    override fun onTabChange(taskStatusUi: TaskStatusUi) {
        _state.update { it.copy(currentSelectedTaskStatusUi = taskStatusUi) }
        loadTasksForDate(_state.value.currentDate)
    }

    override fun onUpdateSelectedDate(dateInMillis: Long) {
        val updatedDate = dateTimeHandler.getLocalDateFromMillis(dateInMillis)
        loadTasksForDate(updatedDate)
    }

    override fun onNextMonthClicked() {
        val updatedDate = _state.value.currentDate.plus(DatePeriod(months = 1))
        loadTasksForDate(updatedDate)
    }

    override fun onPreviousMonthClicked() {
        val updatedDate = _state.value.currentDate.minus(DatePeriod(months = 1))
        loadTasksForDate(updatedDate)
    }

    override fun onClickDateDialogButton() {
        _state.update { it.copy(isDateDialogVisible = true) }
    }

    override fun onDismissDateDialogButton() {
        _state.update { it.copy(isDateDialogVisible = false) }
    }

    override fun onDeleteTaskClicked(taskUi: TaskUi) {
        _state.update {
            it.copy(
                selectedDeleteTaskId = taskUi.id,
                isDeleteBottomSheetVisible = true
            )
        }
    }

    override fun onConfirmDeletedTheTask() {
        tryToExecute {
            taskService.deleteTask(_state.value.selectedDeleteTaskId!!)
            loadTasksForDate(_state.value.currentDate)
            _effect.emit(TasksEffect.ShowSuccessDeleteTaskSnackBar())
        }
    }

    override fun onDismissDeleteBottomSheet() {
        _state.update { it.copy(selectedDeleteTaskId = null, isDeleteBottomSheetVisible = false) }
    }

    override fun onChangeTaskStatusToDoneClicked(taskUi: TaskUi) {
        tryToExecute {
            taskService.editTask(taskUi.toTask().copy(status = Task.Status.DONE))
            _state.update { it.copy(selectedTask = it.selectedTask!!.copy(status = TaskStatusUi.DONE)) }
        }
    }

    override fun onSelectedDayChange(dayNumber: Int) {
        val currentDate = _state.value.currentDate
        val updatedDate = LocalDate(
            year = currentDate.year,
            month = currentDate.month,
            dayOfMonth = dayNumber
        )
        loadTasksForDate(updatedDate)
    }

    override fun onTaskClicked(task: TaskUi) {
        _state.update { it.copy(isDetailsBottomSheetVisible = true, selectedTask = task) }
    }

    override fun onDismissDetailsBottomSheet() {
        _state.update { it.copy(isDetailsBottomSheetVisible = false) }
    }

    override fun onEditTaskClicked() {
        _state.update {
            it.copy(
                isEditBottomSheetVisible = true,
                isDetailsBottomSheetVisible = false
            )
        }
    }

    override fun onDismissEditBottomSheet() {
        _state.update { it.copy(isEditBottomSheetVisible = false) }
    }

    private fun loadTasksForDate(date: LocalDate) {
        tryToExecute {
            getTasksByDate(date).collect { tasks ->
                _state.update { currentState ->
                    currentState.copy(
                        currentDate = date,
                        tasks = tasks.filter { it.status == currentState.currentSelectedTaskStatusUi }
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
        viewModelScope.launch {
            try {
                function()
            } catch (e: Exception) {
                _effect.emit(TasksEffect.ShowFailedSnackBar())
            }
        }
    }


}
