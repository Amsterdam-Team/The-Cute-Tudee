package com.amsterdam.cutetudee.presentation.screens.category

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amsterdam.cutetudee.domain.entity.Category
import com.amsterdam.cutetudee.domain.service.CategoryService
import com.amsterdam.cutetudee.presentation.screens.category.mappers.toCategoryItemUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class, ExperimentalCoroutinesApi::class)
class CategoryViewModel(
    private val categoryService: CategoryService,
) : ViewModel(), CategoryInteractionListener, CategoryAddInteractionListener {

    private val _state = MutableStateFlow(CategoryScreenUiState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<CategoryEffect>()
    val effect = _effect.asSharedFlow()

    private fun updateState(updater: (CategoryScreenUiState) -> CategoryScreenUiState) {
        _state.update(updater)
    }

    private fun sendNewEffect(effect: CategoryEffect) {
        viewModelScope.launch(Dispatchers.IO) {
            _effect.emit(effect)
        }
    }

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                categoryService.getAllCategories().collectLatest { category ->
                    updateState {
                        it.copy(
                            categories = category.map { it.toCategoryItemUiState() }
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("Err", "${e.message}")
            }
        }
    }

    private fun shouldEnableButton(name: String, uri: Uri) {
        if (name != "" && uri != Uri.EMPTY) {
            updateState {
                it.copy(
                    addBottomSheet = it.addBottomSheet.copy(
                        isEnabled = true
                    )
                )
            }
        } else {
            updateState {
                it.copy(
                    addBottomSheet = it.addBottomSheet.copy(
                        isEnabled = false
                    )
                )
            }
        }
    }

    override fun onFABClicked() {
        updateState {
            it.copy(
                hideBottomSheet = false,
            )
        }
    }

    override fun onAddCategoryClicked() {
        updateState {
            it.copy(
                addBottomSheet = it.addBottomSheet.copy(
                    isLoading = true
                )
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                categoryService.addCategory(
                    Category(
                        image = state.value.addBottomSheet.image.toString(),
                        name = state.value.addBottomSheet.name,
                        numberOfTasks = 0,
                        isUserCreated = true
                    )
                )
                updateState {
                    it.copy(
                        hideBottomSheet = true,
                        addBottomSheet = BottomSheetState()
                    )
                }
                sendNewEffect(CategoryEffect.ShowAddSnackBar)
            } catch (e: Exception) {
                updateState {
                    it.copy(
                        addBottomSheet = it.addBottomSheet.copy(
                            isLoading = false,
                        )
                    )
                }
                sendNewEffect(CategoryEffect.ShowError)
            }
        }
    }

    override fun onUpdateCategoryImage(uri: Uri) {
        updateState {
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

    override fun onUpdateCategoryTextValue(text: String) {
        updateState {
            it.copy(
                addBottomSheet = it.addBottomSheet.copy(
                    name = text,
                )
            )
        }
        shouldEnableButton(
            name = state.value.addBottomSheet.name,
            uri = state.value.addBottomSheet.image
        )
    }

    override fun onCancelAddCategoryClicked() {
        updateState {
            it.copy(
                hideBottomSheet = true,
                addBottomSheet = it.addBottomSheet.copy(
                    name = "",
                    image = Uri.EMPTY
                )
            )
        }
    }

    override fun onDismissAddSheet() {
        updateState {
            it.copy(
                hideBottomSheet = true
            )
        }
    }
}