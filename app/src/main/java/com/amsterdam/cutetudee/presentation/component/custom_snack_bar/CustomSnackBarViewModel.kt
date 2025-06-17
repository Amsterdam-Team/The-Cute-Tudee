package com.amsterdam.cutetudee.presentation.component.custom_snack_bar

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CustomSnackBarViewModel : ViewModel() {
    private val _snackBarState = MutableStateFlow<CustomSnackBarState?>(null)
    val snackBarState = _snackBarState.asStateFlow()

    fun showSnackBar(status: CustomSnackBarStatus, message: String) {
        _snackBarState.value = CustomSnackBarState(message, status)
    }

    fun hideSnackBar() {
        _snackBarState.value = null
    }
}