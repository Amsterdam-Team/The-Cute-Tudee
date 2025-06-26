package com.amsterdam.cutetudee.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amsterdam.cutetudee.domain.service.AppSettingsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val appSettingsService: AppSettingsService
) : ViewModel() {

    private val _state = MutableStateFlow<Boolean?>(null)
    val state: StateFlow<Boolean?> get() = _state

    init {
        tryToExecute {
            val isOnBoardingShown = appSettingsService.getOnBoardingIsShown()
            _state.value = isOnBoardingShown
        }
    }

    private fun tryToExecute(function: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                function()
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = null
            }
        }
    }
}