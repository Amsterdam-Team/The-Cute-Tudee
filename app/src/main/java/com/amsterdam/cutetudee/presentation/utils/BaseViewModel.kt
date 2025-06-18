package com.amsterdam.cutetudee.presentation.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                val errorResourceId = exception.mapExceptionToResourceId()
                onError(errorResourceId)
            }
        }
    }



}