package com.amsterdam.cutetudee.presentation.screens.categoryDetails

import android.net.Uri
import androidx.compose.ui.graphics.painter.Painter
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.amsterdam.cutetudee.domain.model.Category
import com.amsterdam.cutetudee.domain.model.Task
import com.amsterdam.cutetudee.domain.service.CategoryService
import com.amsterdam.cutetudee.domain.service.TaskService
import com.amsterdam.cutetudee.presentation.base.BaseViewModel
import com.amsterdam.cutetudee.presentation.navigation.Screen
import com.amsterdam.cutetudee.presentation.utils.UriToBitmapString
import com.amsterdam.cutetudee.presentation.utils.ValidateImageSize
import com.amsterdam.cutetudee.presentation.utils.toBase46eString
import com.amsterdam.cutetudee.presentation.utils.toBitmap
import kotlinx.coroutines.flow.MutableStateFlow
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
    private val validateImageSize: ValidateImageSize,
    private val uriToBitmapString: UriToBitmapString
) : BaseViewModel<CategoryDetailsUiState>(CategoryDetailsUiState()) {

    private val categoryId: String = savedStateHandle.toRoute<Screen.CategoryDetails>().categoryId

//    private val _state = MutableStateFlow(CategoryDetailsUiState())
//    val state: StateFlow<CategoryDetailsUiState> = _state.asStateFlow()

    init {
        loadCategory(categoryId)
    }

    private val _stateFilter = MutableStateFlow(Task.Status.IN_PROGRESS)
    val stateFilter = _stateFilter.asStateFlow()

    @OptIn(ExperimentalUuidApi::class)
    private fun loadCategory(categoryId: String) {
        viewModelScope.launch {
            _state.value = CategoryDetailsUiState(isLoading = true)
            try {
                val categoryIdUuid = Uuid.parse(categoryId)
                val tasks = taskService.getTasksByCategoryId(categoryIdUuid).first()
                val category =
                    categoryService.getAllCategories().first().first { it.id == categoryIdUuid }

                _state.value = CategoryDetailsUiState(
                    isLoading = false,
                    taskUiState = tasks.map { it.toTaskUiState() },
                    categoryUiState = category.toCategoryUiState()
                )
            } catch (e: Exception) {
                _state.value = CategoryDetailsUiState(
                    isLoading = false,
                    errorMessage = e.message ?: "Unexpected Error"
                )
            }
        }
    }

    fun setStatus(status: Task.Status) {
        _stateFilter.value = status
    }


    // region Add Category
    fun updateCategoryName(name: String) {
        _state.update {
            it.copy(
                addBottomSheet = it.addBottomSheet.copy(
                    name = name,
                )
            )
        }
        shouldEnableButton(
            name = _state.value.addBottomSheet.name,
            uri = _state.value.addBottomSheet.image
        )
    }

    fun updateCategoryImage(uri: Uri) {
        _state.update {
            it.copy(
                addBottomSheet = it.addBottomSheet.copy(
                    image = uri,
                )
            )
        }
        shouldEnableButton(
            name = _state.value.addBottomSheet.name,
            uri = _state.value.addBottomSheet.image
        )
    }

    private fun shouldEnableButton(name: String, painter: Painter? = null, uri: Uri? = null) {
        if (name != "") {
            _state.update {
                it.copy(
                    addBottomSheet = it.addBottomSheet.copy(
                        isEnabled = true
                    )
                )
            }
        } else {
            _state.update {
                it.copy(
                    addBottomSheet = it.addBottomSheet.copy(
                        isEnabled = false
                    )
                )
            }
        }
    }

    fun dismissBottomSheet() {
        _state.update {
            it.copy(
                addBottomSheet = it.addBottomSheet.copy(
                    name = "",
                    image = Uri.EMPTY
                ),
                hideBottomSheet = true
            )
        }
    }

    fun onToggleBottomSheet(painter: Painter? = null) {
        _state.update {
            it.copy(
                hideBottomSheet = !it.hideBottomSheet,
                addBottomSheet = it.addBottomSheet.copy(
                    name = state.value.categoryUiState.title,
                    painter = painter
                )
            )
        }
        shouldEnableButton(state.value.categoryUiState.title, painter = painter)
    }

    @OptIn(ExperimentalUuidApi::class)
    fun editCategory() {
        if (_state.value.addBottomSheet.image != Uri.EMPTY && !validateImageSize(_state.value.addBottomSheet.image)) {
            return
        }
        _state.update {
            it.copy(
                addBottomSheet = it.addBottomSheet.copy(
                    isLoading = true
                )
            )
        }

        tryToExecute(
            function = {
                val newImage = if (_state.value.addBottomSheet.image != Uri.EMPTY) {
                    uriToBitmapString.uriToBase64(_state.value.addBottomSheet.image)
                } else {
                    state.value.addBottomSheet.painter!!.toBitmap().toBase46eString()
                }
                categoryService.addCategory(
                    Category(
                        id = Uuid.parse(state.value.categoryUiState.id),
                        image = newImage,
                        name = state.value.addBottomSheet.name,
                        numberOfTasks = 0,
                        isUserCreated = true
                    )
                )
            },
            onSuccess = {
                _state.update {
                    it.copy(
                        hideBottomSheet = true,
                        addBottomSheet = it.addBottomSheet.copy(
                            isLoading = false
                        )
                    )
                }
            },
            onError = { stringRes ->
                _state.update {
                    it.copy(
                        addBottomSheet = it.addBottomSheet.copy(
                            isLoading = false,
                            error = stringRes
                        )
                    )
                }
            }
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    fun deleteCategory() {
        _state.update {
            it.copy(
                addBottomSheet = it.addBottomSheet.copy(
                    isLoading = true
                )
            )
        }

        tryToExecute(
            function = {
                categoryService.deleteCategory(Uuid.parse(state.value.categoryUiState.id))
            },
            onSuccess = {
                _state.update {
                    it.copy(
                        hideBottomSheet = true,
                        addBottomSheet = it.addBottomSheet.copy(
                            isLoading = false
                        )
                    )
                }
            },
            onError = { stringRes ->
                _state.update {
                    it.copy(
                        addBottomSheet = it.addBottomSheet.copy(
                            isLoading = false,
                            error = stringRes
                        )
                    )
                }
            }
        )
    }
    // endregion
}