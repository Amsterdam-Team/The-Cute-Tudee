package com.amsterdam.cutetudee.presentation.utils

import com.amsterdam.cutetudee.domain.service.AppSettingsService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ThemeManager(
    private val appSettingsService: AppSettingsService,
) {

    private val _themeFlow = MutableStateFlow<Boolean?>(null)
    val themeFlow: StateFlow<Boolean?> = _themeFlow.asStateFlow()

    suspend fun initialize() {
        _themeFlow.value = appSettingsService.isDarkMode()
    }

    suspend fun updateTheme(isDarkMode: Boolean) {
        appSettingsService.setDarkMode(isDarkMode)
        _themeFlow.value = isDarkMode
    }
}