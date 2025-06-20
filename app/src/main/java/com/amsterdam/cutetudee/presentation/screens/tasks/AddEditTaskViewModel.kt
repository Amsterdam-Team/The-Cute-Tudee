package com.amsterdam.cutetudee.presentation.screens.tasks

import com.amsterdam.cutetudee.domain.model.Category
import com.amsterdam.cutetudee.domain.model.Task
import com.amsterdam.cutetudee.domain.service.CategoryService
import com.amsterdam.cutetudee.domain.service.TaskService
import com.amsterdam.cutetudee.presentation.base.BaseViewModel
import com.amsterdam.cutetudee.presentation.component.chip.priority.PriorityUi
import com.amsterdam.cutetudee.presentation.utils.IDateTimeHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class AddEditTaskViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService,
    private val dateTimeHandler: IDateTimeHandler
) : BaseViewModel<AddEditTaskUiState>(AddEditTaskUiState()) {

    private val _errorState = MutableStateFlow(Int)
    val errorState = _errorState.asStateFlow()

    fun onTaskNameChanged(updatedTaskName: String) {
        _state.update { state ->
            state.copy(taskName = updatedTaskName)
        }
    }

    fun onTaskDescriptionChanged(updatedTaskDescription: String) {
        _state.update { state ->
            state.copy(taskName = updatedTaskDescription)
        }
    }

    fun onDateChanged(date: Long) {
        _state.update { state ->
            state.copy(date = dateTimeHandler.getLocalDateFromMillis(date))
        }
    }

    fun onCategorySelected(categoryId: String) {
        _state.update { state -> state.copy(selectedCategoryId = categoryId) }
    }

    fun onAction() {
        when (_state.value.taskAction) {
            AddEditTaskUiState.TaskAction.ADD -> addTask()
            AddEditTaskUiState.TaskAction.EDIT -> editTask()
        }
    }

    private fun editTask() {
//        taskService.editTask(
//            Task(
//                _state.value.taskId,
//                _state.value.taskName,
//                _state.value.description,
//                _state.value.date,
//                _state.value.selectedCategoryId
//            )
//        )
    }

    private fun addTask() {
//        TaskService.addTask(
//            Task(
//                _state.value.taskId,
//                _state.value.taskName,
//                _state.value.description,
//                _state.value.date,
//                _state.value.selectedCategoryId
//            )
//        )
    }

    @OptIn(ExperimentalUuidApi::class)
    fun loadTask(taskId: Uuid, taskAction: AddEditTaskUiState.TaskAction) {
        tryToExecute(
            function = { taskService.getTaskById(taskId) },
            onSuccess = { task ->
                this.loadCategory(task, taskAction)
            },
            onError = { errorResourceId -> /*_errorState.update { _ -> errorResourceId }*/ },
            dispatcher = Dispatchers.IO
        )
    }


    private fun loadCategory(task: Task, taskAction: AddEditTaskUiState.TaskAction) {
        tryToExecute(
            function = {
                categoryService.getAllCategories()
                    .collectLatest { categories ->
                        this.updateUiState(
                            task,
                            categories,
                            taskAction
                        )
                    }
            },
            onSuccess = { },
            onError = { errorResourceId -> /*_errorState.update { value -> errorResourceId }*/ },
            dispatcher = Dispatchers.IO
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun updateUiState(
        task: Task,
        categories: List<Category>,
        taskAction: AddEditTaskUiState.TaskAction
    ) {
        _state.update { state ->
            state.copy(
                taskName = task.title,
                description = task.description ?: "",
                date = task.targetDate,
                priority = PriorityUi.valueOf(task.priority.name),
                taskAction = taskAction,
                selectedCategoryId = task.categoryId.toString(),
                categories = categories.map { category -> category.toCategoryItemUiState() }
            )
        }
    }
}