package com.amsterdam.cutetudee.presentation.utils

import com.amsterdam.cutetudee.domain.model.ThemeMode
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
        val storedTheme = appSettingsService.getPreferredMode()
        _themeFlow.value = storedTheme == ThemeMode.DARK
    }

    suspend fun updateTheme(theme: Boolean) {
        appSettingsService.setPreferredMode(if (theme) ThemeMode.DARK else ThemeMode.LIGHT)
        _themeFlow.value = theme
    }
}