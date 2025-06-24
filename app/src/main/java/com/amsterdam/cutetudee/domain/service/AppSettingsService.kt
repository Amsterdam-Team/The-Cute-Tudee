package com.amsterdam.cutetudee.domain.service

import kotlinx.coroutines.flow.Flow

interface AppSettingsService {
    suspend fun setOnboardingCompleted()
    suspend fun getOnBoardingIsShown(): Boolean
    suspend fun isDarkMode():Flow<Boolean>
    suspend fun setDarkMode(isDarkMode: Boolean)
}