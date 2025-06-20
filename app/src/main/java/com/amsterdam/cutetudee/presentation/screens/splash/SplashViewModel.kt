package com.amsterdam.cutetudee.presentation.screens.splash

import com.amsterdam.cutetudee.domain.service.AppSettingsService
import com.amsterdam.cutetudee.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.update

class SplashViewModel(
    private val appSettingsService: AppSettingsService
) : BaseViewModel<Boolean?>(
    initialState = null
) {
    init {
        tryToExecute(
            function = { appSettingsService.getOnBoardingIsShown() },
            onSuccess = { isOnBoardingShown -> _state.update { isOnBoardingShown } },
            onError = { _state.update { false } },
        )
    }
}