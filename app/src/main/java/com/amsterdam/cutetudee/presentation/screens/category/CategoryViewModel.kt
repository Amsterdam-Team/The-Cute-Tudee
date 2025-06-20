package com.amsterdam.cutetudee.presentation.screens.category

import androidx.lifecycle.viewModelScope
import com.amsterdam.cutetudee.domain.model.Category
import com.amsterdam.cutetudee.domain.service.CategoryService
import com.amsterdam.cutetudee.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi

class CategoryViewModel(private val categoryService: CategoryService) :
    BaseViewModel<CategoryScreenUiState>(CategoryScreenUiState()) {

    init {
        loadCategories()
    }

    private fun loadCategories() {
        _state.value = _state.value.copy(isLoading = true, errorMessageResourceId = null)
        tryToExecute(
            function = {
                viewModelScope.launch {
                    categoryService.getAllCategories().collectLatest { categories ->
                        val categoryItems = categories.map { category ->
                            mapCategoryToUiState(category)
                        }
                        _state.value = _state.value.copy(
                            categories = categoryItems,
                            isLoading = false,
                            errorMessageResourceId = null
                        )
                    }
                }
            },
            onSuccess = {

            },
            onError = { errorResourceId ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessageResourceId = errorResourceId
                )
            }
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    fun mapCategoryToUiState(category: Category): CategoryUiState {
        return CategoryUiState(
            categoryId = category.id.toString(),
            categoryImage = category.image,
            categoryName = category.name,
            badgeCount = category.numberOfTasks.toString()
        )
    }
}