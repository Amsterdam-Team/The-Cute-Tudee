package com.amsterdam.cutetudee.domain.repository

import com.amsterdam.cutetudee.domain.model.ThemeMode

interface AppSettingsRepository {
    fun setOnBoardingIsShownToTrue()
    fun getOnBoardingIsShown(): Boolean
    fun getPreferredMode():ThemeMode
    fun setPreferredMode(themeMode: ThemeMode)
}