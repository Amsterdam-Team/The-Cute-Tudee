package com.amsterdam.cutetudee.data.service

import com.amsterdam.cutetudee.data.local.datastore.AppPreferences
import com.amsterdam.cutetudee.data.mapper.toBoolean
import com.amsterdam.cutetudee.data.mapper.toThemeMode
import com.amsterdam.cutetudee.domain.service.AppSettingsService
import com.amsterdam.cutetudee.domain.utils.ThemeMode
import kotlinx.coroutines.flow.Flow

class AppSettingsServiceImpl(
    private val appPreferences: AppPreferences
) : AppSettingsService {
    override suspend fun setOnboardingCompleted() = appPreferences.setIsOnBoardingFinished(true)

    override suspend fun getOnBoardingIsShown(): Boolean = appPreferences.getIsOnBoardingFinished()

    override suspend fun getThemeMode(): Flow<ThemeMode> =
        appPreferences.getAppDarkModeOn().toThemeMode()

    override suspend fun setThemeMode(themeMode: ThemeMode) =
        appPreferences.setAppDarkModeOn(themeMode.toBoolean())
}