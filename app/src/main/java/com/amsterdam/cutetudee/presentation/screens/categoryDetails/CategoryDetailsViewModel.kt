@file:OptIn(ExperimentalUuidApi::class)

package com.amsterdam.cutetudee.presentation.screens.categoryDetails

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.amsterdam.cutetudee.domain.entity.Category
import com.amsterdam.cutetudee.domain.entity.Task
import com.amsterdam.cutetudee.domain.service.CategoryService
import com.amsterdam.cutetudee.domain.service.TaskService
import com.amsterdam.cutetudee.presentation.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CategoryDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val taskService: TaskService,
    private val categoryService: CategoryService,
) : ViewModel(), CategoryDetailsInteractionListener, CategoryEditInteractionListener,
    CategoryDeleteConfirmationInteractionListener {

    private val categoryId: String = savedStateHandle.toRoute<Screen.CategoryDetails>().categoryId

    private val _state = MutableStateFlow(CategoryDetailsUiState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<CategoryDetailsEffect>()
    val effect = _effect.asSharedFlow()

    private val _stateFilter = MutableStateFlow(Task.Status.IN_PROGRESS)
    val stateFilter = _stateFilter.asStateFlow()

    private fun updateState(updater: (CategoryDetailsUiState) -> CategoryDetailsUiState) {
        _state.update(updater)
    }

    private fun sendNewEffect(effect: CategoryDetailsEffect) {
        viewModelScope.launch(Dispatchers.IO) {
            _effect.emit(effect)
        }
    }

    init {
        getCategoryDetails()
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun getCategoryDetails() {
        updateState { it.copy(isLoading = true) }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val tasks = taskService.getTasksByCategoryId(categoryId.toUuid()).first()
                val category = categoryService.getAllCategories().first().first(::onFilterById)

                updateState {
                    it.copy(
                        isLoading = false,
                        taskUiState = tasks.map { task -> task.toTaskUiState() },
                        categoryItemUiState = category.toCategoryItemUiState()
                    )
                }
            } catch (e: Exception) {
                updateState {
                    it.copy(
                        isLoading = false
                    )
                }
                sendNewEffect(CategoryDetailsEffect.ShowError)
            }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun onFilterById(category: Category): Boolean {
        return category.id == categoryId.toUuid()
    }

    override fun onTaskStatusChanged(taskStatus: Task.Status) {
        _stateFilter.value = taskStatus
    }

    override fun onEditOptionClicked(name: String, uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val selectedUriImage =
                    state.value.categoryBottomSheetState.image.takeIf { it != Uri.EMPTY } ?: uri

                updateState {
                    it.copy(
                        hideEditBottomSheet = false,
                        categoryBottomSheetState = it.categoryBottomSheetState.copy(
                            image = selectedUriImage,
                            name = state.value.categoryBottomSheetState.name.ifEmpty { name }
                        )
                    )
                }
            } catch (e: Exception) {
                updateState {
                    it.copy(
                        isLoading = false,
                    )
                }
                sendNewEffect(CategoryDetailsEffect.ShowError)
            }
        }
    }

    override fun onNavigateBackClicked() {
        sendNewEffect(CategoryDetailsEffect.NavigateBack)
    }

    override fun onUpdateCategoryImage(uri: Uri) {
        updateState {
            it.copy(
                categoryBottomSheetState = it.categoryBottomSheetState.copy(
                    image = uri,
                )
            )
        }
    }

    override fun onUpdateCategoryTextValue(text: String) {
        updateState {
            it.copy(
                categoryBottomSheetState = it.categoryBottomSheetState.copy(
                    name = text,
                    isEnabled = text.isNotBlank()
                )
            )
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun onSaveCategoryClicked() {
        updateLoadingState()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                categoryService.addCategory(
                    Category(
                        id = Uuid.parse(state.value.categoryItemUiState.id),
                        image = _state.value.categoryBottomSheetState.image.toString(),
                        name = state.value.categoryBottomSheetState.name,
                        numberOfTasks = state.value.taskUiState.size,
                        isUserCreated = true
                    )
                )
                updateEditSuccessState()
            } catch (e: Exception) {
                updateState {
                    it.copy(
                        isLoading = false,
                    )
                }
                sendNewEffect(CategoryDetailsEffect.ShowError)
            }
        }
    }

    override fun onDeleteCategoryClicked() {
        updateState {
            it.copy(
                hideEditBottomSheet = true,
                showDeleteConfirmBottomSheet = true
            )
        }
    }

    override fun onCancelEditCategoryClicked() {
        clearEditState()
    }

    override fun onDismissEditSheet() {
        updateState {
            it.copy(
                hideEditBottomSheet = true,
            )
        }
    }


    override fun onDeleteConfirmationClicked() {
        updateLoadingState()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                categoryService.deleteCategory(state.value.categoryItemUiState.id.toUuid())
                updateState {
                    it.copy(
                        hideEditBottomSheet = true,
                        showDeleteConfirmBottomSheet = false
                    )
                }
                sendNewEffect(CategoryDetailsEffect.ShowDeleteSnackBar)
                sendNewEffect(CategoryDetailsEffect.NavigateBack)
            } catch (e: Exception) {
                updateErrorState()
            }
        }
    }

    override fun onCancelDeleteConfirmationClicked() {
        updateState {
            it.copy(
                hideEditBottomSheet = false,
                showDeleteConfirmBottomSheet = false
            )
        }
    }

    override fun onDismissDeleteConfirmationSheet() {
        updateState {
            it.copy(
                hideEditBottomSheet = false,
                showDeleteConfirmBottomSheet = false
            )
        }
    }


    private fun clearEditState() {
        updateState {
            it.copy(
                hideEditBottomSheet = true,
                categoryBottomSheetState = it.categoryBottomSheetState.copy(
                    name = "",
                    image = Uri.EMPTY
                )
            )
        }
    }

    private fun updateEditSuccessState() {
        updateState {
            it.copy(
                hideEditBottomSheet = true,
                categoryBottomSheetState = it.categoryBottomSheetState.copy(
                    isLoading = false
                )
            )
        }
        sendNewEffect(CategoryDetailsEffect.ShowEditSnackBar)
    }

    private fun updateErrorState() {
        updateState {
            it.copy(
                categoryBottomSheetState = it.categoryBottomSheetState.copy(
                    isLoading = false
                )
            )
        }
        sendNewEffect(CategoryDetailsEffect.ShowError)
    }

    private fun updateLoadingState() {
        updateState {
            it.copy(
                categoryBottomSheetState = it.categoryBottomSheetState.copy(
                    isLoading = true
                )
            )
        }
    }
}