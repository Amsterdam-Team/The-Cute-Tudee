package com.amsterdam.cutetudee.data.service

import com.amsterdam.cutetudee.data.local.datastore.AppPreferences
import com.amsterdam.cutetudee.domain.model.ThemeMode
import com.amsterdam.cutetudee.domain.service.AppSettingsService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AppSettingsServiceImpl(
    private val appPreferences: AppPreferences
) : AppSettingsService {
    override suspend fun setOnboardingCompleted() {
        appPreferences.setIsOnBoardingFinished(true)
    }

    override suspend fun getOnBoardingIsShown(): Boolean {
        return appPreferences.getIsOnBoardingFinished()
    }

    override fun getPreferredMode(): Flow<ThemeMode> = flow {
        (appPreferences.getAppDarkModeOn().collect({
            emit(if (it) ThemeMode.DARK else ThemeMode.LIGHT)
        }))
    }


    override suspend fun setPreferredMode(themeMode: ThemeMode) {
        appPreferences.setAppDarkModeOn(themeMode == ThemeMode.DARK)
    }
}