package com.amsterdam.cutetudee.presentation.screens.category

import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
import com.amsterdam.cutetudee.domain.model.Category
import com.amsterdam.cutetudee.domain.repository.CategoryService
import com.amsterdam.cutetudee.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi

class CategoryViewModel(private val categoryService: CategoryService) :
    BaseViewModel<CategoryScreenUiState>(CategoryScreenUiState()) {

        init {
            loadCategories()
            observeCategories()
        }

        @SuppressLint("StateFlowValueCalledInComposition")
        fun loadCategories() {
            _state.value = _state.value.copy(isLoading = true, errorMessageResourceId = null)

            tryToExecute(
                function = { categoryService.getAllCategories().first() },
                onSuccess = { categories ->
                    val categoryItems = categories.map { category ->
                        mapCategoryToUiState(category)
                    }
                    _state.value = _state.value.copy(
                        categories = categoryItems,
                        isLoading = false
                    )
                },
                onError = { errorResourceId ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessageResourceId = errorResourceId
                    )
                }
            )
        }

        private fun observeCategories() {
            viewModelScope.launch {

                categoryService.getAllCategories().collectLatest { categories ->
                    val categoryItems = categories.map { category ->
                        mapCategoryToUiState(category)
                    }
                    _state.value = _state.value.copy(categories = categoryItems)
                }
            }
        }


        @OptIn(ExperimentalUuidApi::class)
        private fun mapCategoryToUiState(category: Category): CategoryUiState {
            return CategoryUiState(
                categoryId = category.id.toString(),
                categoryImage = category.imageUrl,
                categoryName = category.name,
                badgeCount = if (category.numberOfTasks > 0) category.numberOfTasks.toString() else ""
            )
        }

        fun clearErrorMessage() {
            _state.value = _state.value.copy(errorMessageResourceId = null)
        }

    }
