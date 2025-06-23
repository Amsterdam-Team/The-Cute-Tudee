package com.amsterdam.cutetudee.presentation.screens.splash

import com.amsterdam.cutetudee.data.local.datastore.AppPreferencesDataStore
import com.amsterdam.cutetudee.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.update

class SplashViewModel(
    private val appPreferencesDataStore: AppPreferencesDataStore
) : BaseViewModel<Boolean?>(
    initialState = null
) {
    init {
        tryToExecute(
            function = { appPreferencesDataStore.isOnboardingCompleted() },
            onSuccess = { isOnBoardingShown -> _state.update { isOnBoardingShown } },
            onError = { _state.update { false } },
        )
    }
}