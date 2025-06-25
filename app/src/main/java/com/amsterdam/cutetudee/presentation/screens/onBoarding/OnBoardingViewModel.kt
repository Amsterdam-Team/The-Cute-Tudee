package com.amsterdam.cutetudee.presentation.screens.onBoarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amsterdam.cutetudee.domain.service.AppSettingsService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnBoardingViewModel(
    val appSettingsService: AppSettingsService
) : ViewModel(), OnBoardingInteractionListener {
    private val _state = MutableStateFlow(OnboardingUiState())
    val state: StateFlow<OnboardingUiState> = _state

    override fun onFinishClicked() {
        tryToExecute {
            appSettingsService.setOnboardingCompleted()
            _state.update { it.copy(isOnboardingFinished = true) }
        }
    }

    private fun tryToExecute(function: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                function()
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message?.toIntOrNull() ?: -1) }
            }
        }
    }
}