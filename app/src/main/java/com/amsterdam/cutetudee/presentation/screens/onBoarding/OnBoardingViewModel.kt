package com.amsterdam.cutetudee.presentation.screens.onBoarding

import androidx.lifecycle.ViewModel
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.domain.repository.AppSettingsService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class OnBoardingViewModel(
    private val appSettingsService: AppSettingsService
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState = _uiState.asStateFlow()

    fun onFinishClicked() {
       try {
           appSettingsService.setOnBoardingIsShownToTrue()
           _uiState.value = _uiState.value.copy(isOnboardingFinished = true)
       }catch (_: Exception){
           _uiState.value = _uiState.value.copy(error =R.string.error)
       }
    }

}