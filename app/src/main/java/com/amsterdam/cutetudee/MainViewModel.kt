package com.amsterdam.cutetudee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amsterdam.cutetudee.domain.model.ThemeMode
import com.amsterdam.cutetudee.domain.service.AppSettingsService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val appSettingsService: AppSettingsService
) : ViewModel() {
    val _themeState: MutableStateFlow<ThemeMode> = MutableStateFlow(ThemeMode.LIGHT)
    val themeState = _themeState.asStateFlow()

    init {
        viewModelScope.launch {
            appSettingsService.getPreferredMode().collect { mode ->
                _themeState.value = mode
            }
        }
    }
}