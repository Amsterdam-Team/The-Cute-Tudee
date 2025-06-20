package com.amsterdam.cutetudee.presentation.screens.categoryDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.amsterdam.cutetudee.domain.model.Task
import com.amsterdam.cutetudee.domain.service.CategoryService
import com.amsterdam.cutetudee.domain.service.TaskService
import com.amsterdam.cutetudee.presentation.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CategoryDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val taskService: TaskService,
    private val categoryService: CategoryService,
) : ViewModel() {

    private val categoryId: String = savedStateHandle.toRoute<Screen.CategoryDetails>().categoryId

    private val _uiState = MutableStateFlow(CategoryDetailsUiState())
    val uiState: StateFlow<CategoryDetailsUiState> = _uiState.asStateFlow()


    init {
        loadCategory(categoryId)
    }

    private val _stateFilter = MutableStateFlow(Task.Status.IN_PROGRESS)
    val stateFilter = _stateFilter.asStateFlow()

    @OptIn(ExperimentalUuidApi::class)
    private fun loadCategory(categoryId: String) {
        viewModelScope.launch {
            _uiState.value = CategoryDetailsUiState(isLoading = true)
            try {
                val categoryIdUuid = Uuid.parse(categoryId)
                val tasks = taskService.getTasksByCategoryId(categoryIdUuid).first()
                val category =
                    categoryService.getAllCategories().first().first { it.id == categoryIdUuid }

                _uiState.value = CategoryDetailsUiState(
                    isLoading = false,
                    taskUiState = tasks.map { it.toTaskUiState() },
                    categoryUiState = category.toCategoryUiState()
                )
            } catch (e: Exception) {
                _uiState.value = CategoryDetailsUiState(
                    isLoading = false,
                    errorMessage = e.message ?: "Unexpected Error"
                )
            }
        }
    }

    fun setStatus(status: Task.Status) {
        _stateFilter.value = status
    }
}