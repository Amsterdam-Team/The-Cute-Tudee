package com.amsterdam.cutetudee.domain.service

import com.amsterdam.cutetudee.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow

interface AppSettingsService {
    suspend fun setOnboardingCompleted()
    suspend fun getOnBoardingIsShown(): Boolean
    fun getPreferredMode(): Flow<ThemeMode>
    suspend fun setPreferredMode(themeMode: ThemeMode)
}