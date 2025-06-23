@file:OptIn(ExperimentalUuidApi::class)

package com.amsterdam.cutetudee.presentation.screens.categoryDetails

import android.net.Uri
import androidx.compose.ui.graphics.painter.Painter
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.amsterdam.cutetudee.domain.model.Category
import com.amsterdam.cutetudee.domain.model.Task
import com.amsterdam.cutetudee.domain.service.CategoryService
import com.amsterdam.cutetudee.domain.service.TaskService
import com.amsterdam.cutetudee.presentation.base.mapExceptionToResourceId
import com.amsterdam.cutetudee.presentation.navigation.Screen
import com.amsterdam.cutetudee.presentation.utils.UriToBitmapString
import com.amsterdam.cutetudee.presentation.utils.toBase46eString
import com.amsterdam.cutetudee.presentation.utils.toBitmap
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
    private val uriToBitmapString: UriToBitmapString
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
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    init {
        getCategoryDetails()
    }

    private fun getCategoryDetails() {
        updateState { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                val tasks = taskService.getTasksByCategoryId(categoryId.toUuid()).first()
                val category = categoryService.getAllCategories().first().first(::onFilterById)

                updateState {
                    it.copy(
                        isLoading = false,
                        taskUiState = tasks.map { task -> task.toTaskUiState() },
                        categoryItemUiState = category.toCategoryItemUiState(
                            inProgressTasksCount = countTasksByStatus(
                                tasks,
                                Task.Status.IN_PROGRESS
                            ),
                            toDoTasksCount = countTasksByStatus(tasks, Task.Status.TODO),
                            doneTasksCount = countTasksByStatus(tasks, Task.Status.DONE),
                        )
                    )
                }
            } catch (e: Exception) {
                updateState {
                    it.copy(
                        isLoading = false, errorMessage = e.message ?: "Unexpected Error"
                    )
                }
                sendNewEffect(CategoryDetailsEffect.ShowError)
            }
        }
    }

    private fun onFilterById(category: Category): Boolean {
        return category.id == categoryId.toUuid()
    }

    private fun countTasksByStatus(tasks: List<Task>, status: Task.Status): Int {
        return tasks.count { it.status == status }
    }

    override fun onTaskStatusChanged(taskStatus: Task.Status) {
        _stateFilter.value = taskStatus
    }

    override fun onEditOptionClicked(name: String, painter: Painter) {
        viewModelScope.launch {
            try {
                val selectedUriImage = state.value.addBottomSheet.image
                    .takeIf { it != Uri.EMPTY } ?: uriToBitmapString.bitmapToUri(painter.toBitmap())

                updateState {
                    it.copy(
                        hideEditBottomSheet = false,
                        addBottomSheet = it.addBottomSheet.copy(
                            image = selectedUriImage,
                            name = state.value.addBottomSheet.name.ifEmpty { name }
                        )
                    )
                }
            } catch (e: Exception) {
                updateState {
                    it.copy(
                        isLoading = false, errorMessage = e.message ?: "Unexpected Error"
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
                addBottomSheet = it.addBottomSheet.copy(
                    image = uri,
                )
            )
        }
    }

    override fun onUpdateCategoryTextValue(text: String) {
        updateState {
            it.copy(
                addBottomSheet = it.addBottomSheet.copy(
                    name = text,
                    isEnabled = text.isNotBlank()
                )
            )
        }
    }

    override fun onSaveCategoryClicked() {
        updateLoadingState()
        viewModelScope.launch {
            try {
                val newImage = if (_state.value.addBottomSheet.image != Uri.EMPTY) {
                    uriToBitmapString.uriToBase64(_state.value.addBottomSheet.image)
                } else {
                    state.value.addBottomSheet.painter!!.toBitmap().toBase46eString()
                }
                categoryService.addCategory(
                    Category(
                        id = Uuid.parse(state.value.categoryItemUiState.id),
                        image = newImage,
                        name = state.value.addBottomSheet.name,
                        numberOfTasks = state.value.taskUiState.size,
                        isUserCreated = true
                    )
                )
                updateEditSuccessState()
            } catch (e: Exception) {
                updateState {
                    it.copy(
                        isLoading = false, errorMessage = e.message ?: "Unexpected Error"
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
        viewModelScope.launch {
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
                updateErrorState(e)
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
                addBottomSheet = it.addBottomSheet.copy(
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
                addBottomSheet = it.addBottomSheet.copy(
                    isLoading = false
                )
            )
        }
        sendNewEffect(CategoryDetailsEffect.ShowEditSnackBar)
    }

    private fun updateErrorState(e: Exception) {
        updateState {
            it.copy(
                addBottomSheet = it.addBottomSheet.copy(
                    isLoading = false, error = e.mapExceptionToResourceId()
                )
            )
        }
        sendNewEffect(CategoryDetailsEffect.ShowError)
    }

    private fun updateLoadingState() {
        updateState {
            it.copy(
                addBottomSheet = it.addBottomSheet.copy(
                    isLoading = true
                )
            )
        }
    }
}