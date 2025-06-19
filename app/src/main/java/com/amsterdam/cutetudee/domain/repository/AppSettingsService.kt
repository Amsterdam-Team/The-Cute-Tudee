package com.amsterdam.cutetudee.domain.repository

import com.amsterdam.cutetudee.domain.model.ThemeMode

interface AppSettingsService {
    suspend fun setOnBoardingIsShownToTrue()
    suspend fun getOnBoardingIsShown(): Boolean
    suspend fun getPreferredMode():ThemeMode
    suspend fun setPreferredMode(themeMode: ThemeMode)
}