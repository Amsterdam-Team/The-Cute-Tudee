package com.amsterdam.cutetudee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amsterdam.cutetudee.presentation.utils.ThemeManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    val themeManager: ThemeManager
) : ViewModel() {

    val theme = getTheme().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    init {
        viewModelScope.launch {
            initialize()
        }
    }

    private fun getTheme() = themeManager.themeFlow.filterNotNull().distinctUntilChanged()

    suspend fun initialize() {
        themeManager.initialize()
    }
}