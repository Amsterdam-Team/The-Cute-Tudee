package com.amsterdam.cutetudee.presentation.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

open class BaseViewModel<T>(initialState: T) : ViewModel() {

    protected val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.d("coroutineExceptionHandler", "Exception : " + exception.message)
    }

    protected fun <T> tryToExecute(
        function: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (errorResourceId: Int) -> Unit,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) {
        viewModelScope.launch(dispatcher + coroutineExceptionHandler) {
            try {
                val result = function()
                onSuccess(result)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                val errorResId = e.mapExceptionToResourceId()
                onError(errorResId)
            }
        }
    }
}
