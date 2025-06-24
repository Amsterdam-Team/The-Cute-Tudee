package com.amsterdam.cutetudee.presentation.screens.category

import android.net.Uri
import android.util.Log
import androidx.compose.ui.graphics.painter.Painter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.domain.model.Category
import com.amsterdam.cutetudee.domain.service.CategoryService
import com.amsterdam.cutetudee.presentation.component.AddCategoryBottomSheet.CategoryInteractionListener
import com.amsterdam.cutetudee.presentation.screens.category.mappers.toCategoryItemUiState
import com.amsterdam.cutetudee.presentation.utils.UriToBitmapString
import com.amsterdam.cutetudee.presentation.utils.ValidateImageSize
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
    private val validateImageSize: ValidateImageSize,
    private val uriToBitmapString: UriToBitmapString
) : ViewModel(), CategoryInteractionListener {

    private val _state = MutableStateFlow(CategoryScreenUiState())
    val state = _state.asStateFlow()

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

    fun onToggleBottomSheet(painter: Painter? = null) {
        _state.update {
            it.copy(
                hideBottomSheet = !it.hideBottomSheet,
                addBottomSheet = it.addBottomSheet.copy(
                    painter = painter
                )
            )
        }
    }


    override fun deleteCategory() {
        print("")
    }

    override fun upsertCategory() {
        if (!validateImageSize(state.value.addBottomSheet.image)) {
            return
        }
        _state.update {
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
                        image = uriToBitmapString.uriToBase64(state.value.addBottomSheet.image),
                        name = state.value.addBottomSheet.name,
                        numberOfTasks = 0,
                        isUserCreated = true
                    )
                )
                _state.update {
                    it.copy(
                        hideBottomSheet = true,
                        addBottomSheet = it.addBottomSheet.copy(
                            isLoading = false
                        )
                    )
                }
                _effect.emit(CategoryEffect.ShowAddSnackBar)

            } catch (e: Exception) {

                _state.update {
                    it.copy(
                        addBottomSheet = it.addBottomSheet.copy(
                            isLoading = false,
                            error = R.string.error_invalid_category_input
                        )
                    )
                }
                _effect.emit(CategoryEffect.ShowError)
            }
        }
    }

    override fun onDismiss() {
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

    override fun onTextValueChange(text: String) {
        _state.update {
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

    override fun onImageSelected(image: Uri) {
        _state.update {
            it.copy(
                addBottomSheet = it.addBottomSheet.copy(
                    image = image,
                )
            )
        }
        shouldEnableButton(
            name = state.value.addBottomSheet.name,
            uri = state.value.addBottomSheet.image
        )
    }
// endregion
}