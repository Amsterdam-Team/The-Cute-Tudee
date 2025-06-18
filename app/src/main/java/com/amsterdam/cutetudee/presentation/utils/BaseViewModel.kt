package com.amsterdam.cutetudee.presentation.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.domain.exception.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BaseViewModel <T>(initialState: T): ViewModel() {

    protected val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    protected fun <T> tryToExecute(
        function: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (errorResourceId: Int) -> Unit,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ){
        viewModelScope.launch(dispatcher) {
            try {
                val result = function()
                onSuccess(result)
            } catch (exception : Exception){
                val errorResourceId = mapExceptionToResourceId(exception)
                onError(errorResourceId)
            }
        }
    }

    private fun mapExceptionToResourceId(exception: Exception): Int {
        return when (exception) {
            is TaskNotFoundException -> R.string.error_task_not_found
            is NoTasksFoundPerDateException -> R.string.error_no_tasks_found_per_date
            is InvalidTaskInputException -> R.string.error_invalid_task_input
            is InvalidCategoryInputException -> R.string.error_invalid_category_input
            is NoCategoriesFoundException -> R.string.error_no_categories_found
            is CategoryNotFoundException -> R.string.error_category_not_found
            else -> R.string.error_unknown
        }
    }

}