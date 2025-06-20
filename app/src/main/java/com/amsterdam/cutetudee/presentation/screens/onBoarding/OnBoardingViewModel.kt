package com.amsterdam.cutetudee.presentation.screens.onBoarding

import com.amsterdam.cutetudee.domain.service.AppSettingsService
import com.amsterdam.cutetudee.presentation.base.BaseViewModel

class OnBoardingViewModel(
    private val appSettingsService: AppSettingsService
) : BaseViewModel<OnboardingUiState>(OnboardingUiState()) {

    fun onFinishClicked() {
        tryToExecute(
            function = {
                appSettingsService.setOnBoardingIsShownToTrue()
            },
            onSuccess = {
                _state.value = _state.value.copy(isOnboardingFinished = true)
            },
            onError = {
                _state.value = _state.value.copy(error = it)
            }
        )
    }
}