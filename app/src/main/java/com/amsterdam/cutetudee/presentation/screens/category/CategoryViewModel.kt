package com.amsterdam.cutetudee.presentation.screens.category

import android.net.Uri
import android.util.Log
import androidx.compose.ui.graphics.painter.Painter
import androidx.lifecycle.viewModelScope
import com.amsterdam.cutetudee.domain.model.Category
import com.amsterdam.cutetudee.domain.service.CategoryService
import com.amsterdam.cutetudee.presentation.base.BaseViewModel
import com.amsterdam.cutetudee.presentation.screens.category.mappers.toCategoryItemUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class, ExperimentalCoroutinesApi::class)
class CategoryViewModel(
    private val categoryService: CategoryService,
) : BaseViewModel<CategoryScreenUiState>(CategoryScreenUiState()) {

    private val _effect = MutableSharedFlow<CategoryEffect>()
    val effect = _effect.asSharedFlow()

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            try {
                categoryService.getAllCategories().collectLatest { category ->
                    _state.update {
                        it.copy(
                            categories = category.map { it.toCategoryItemUiState() }
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("Err", "${e.message}")
            }

        }
        viewModelScope.launch {
            categoryService.getAllCategories()
                .collectLatest { category ->
                    Log.d("CategoryViewModel", "loadCategories: $category")
                    _state.update {
                        it.copy(
                            categories = category.map { it.toCategoryItemUiState() }
                        )
                    }
                }
        }
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
            name = state.value.addBottomSheet.name,
            uri = state.value.addBottomSheet.image
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
            name = state.value.addBottomSheet.name,
            uri = state.value.addBottomSheet.image
        )
    }

    private fun shouldEnableButton(name: String, uri: Uri) {
        if (name != "" && uri != Uri.EMPTY) {
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

    fun onToggleBottomSheet(image: Uri = Uri.EMPTY) {
        _state.update {
            it.copy(
                hideBottomSheet = !it.hideBottomSheet,
                addBottomSheet = it.addBottomSheet.copy(
                    image = image
                )
            )
        }
    }

    fun addCategory() {
        _state.update {
            it.copy(
                addBottomSheet = it.addBottomSheet.copy(
                    isLoading = true
                )
            )
        }

        tryToExecute(
            function = {
                categoryService.addCategory(
                    Category(
                        image = state.value.addBottomSheet.image.toString(),
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
                viewModelScope.launch(Dispatchers.IO) {
                    _effect.emit(CategoryEffect.ShowAddSnackBar)
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
                viewModelScope.launch(Dispatchers.IO) {
                    _effect.emit(CategoryEffect.ShowError)
                }
            }
        )
    }
    // endregion
}