package com.amsterdam.cutetudee.presentation.screens.onBoarding

import com.amsterdam.cutetudee.data.local.datastore.AppPreferencesDataStore
import com.amsterdam.cutetudee.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class OnBoardingViewModel(
    private val appPreferencesDataStore: AppPreferencesDataStore
) : BaseViewModel<OnboardingUiState>(OnboardingUiState()) {

    fun onFinishClicked() {
        val _state = MutableStateFlow(
            OnboardingUiState(isOnboardingFinished = appPreferencesDataStore.isOnboardingCompleted())
        )
        val state: MutableStateFlow<OnboardingUiState> = _state
        tryToExecute(
            function = {
                appPreferencesDataStore.setOnboardingCompleted()
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