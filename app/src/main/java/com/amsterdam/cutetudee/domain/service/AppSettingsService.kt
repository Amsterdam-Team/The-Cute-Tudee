package com.amsterdam.cutetudee.domain.service

import com.amsterdam.cutetudee.domain.utils.ThemeMode
import kotlinx.coroutines.flow.Flow

interface AppSettingsService {
    suspend fun setOnboardingCompleted()
    suspend fun getOnBoardingIsShown(): Boolean
    suspend fun getThemeMode(): Flow<ThemeMode>
    suspend fun setThemeMode(themeMode: ThemeMode)
}