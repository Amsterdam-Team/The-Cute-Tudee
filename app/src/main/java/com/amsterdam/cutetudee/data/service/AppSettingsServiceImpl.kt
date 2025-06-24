package com.amsterdam.cutetudee.data.service

import com.amsterdam.cutetudee.data.local.datastore.AppPreferences
import com.amsterdam.cutetudee.domain.service.AppSettingsService
import kotlinx.coroutines.flow.Flow

class AppSettingsServiceImpl(
    private val appPreferences: AppPreferences
) : AppSettingsService {
    override suspend fun setOnboardingCompleted() = appPreferences.setIsOnBoardingFinished(true)

    override suspend fun getOnBoardingIsShown(): Boolean = appPreferences.getIsOnBoardingFinished()

    override suspend fun isDarkMode(): Flow<Boolean> = appPreferences.getAppDarkModeOn()

    override suspend fun setDarkMode(isDarkMode: Boolean) = appPreferences.setAppDarkModeOn(isDarkMode)
}