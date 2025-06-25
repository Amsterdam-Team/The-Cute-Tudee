package com.amsterdam.cutetudee.data.mapper

import com.amsterdam.cutetudee.domain.utils.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun Flow<Boolean>.toThemeMode() =
    this.map { isDarkMode -> if (isDarkMode) ThemeMode.DARK else ThemeMode.LIGHT }

fun ThemeMode.toBoolean() = this == ThemeMode.DARK