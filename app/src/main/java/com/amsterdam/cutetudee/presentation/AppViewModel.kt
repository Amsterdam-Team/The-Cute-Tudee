package com.amsterdam.cutetudee.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amsterdam.cutetudee.domain.service.AppSettingsService
import com.amsterdam.cutetudee.domain.utils.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppViewModel(
    private val appSettingsService: AppSettingsService
) : ViewModel() {
    private val _themeState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val themeState = _themeState.asStateFlow()

    init {
        viewModelScope.launch {
            appSettingsService.getThemeMode().collect { mode ->
                _themeState.value = mode == ThemeMode.DARK
            }
        }
    }
}