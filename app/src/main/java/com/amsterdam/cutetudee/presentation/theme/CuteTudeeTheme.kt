package com.amsterdam.cutetudee.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.amsterdam.cutetudee.presentation.theme.colors.LocalCuteTudeeLocalColors
import com.amsterdam.cutetudee.presentation.theme.colors.darkThemeColors
import com.amsterdam.cutetudee.presentation.theme.colors.lightThemeColors

@Composable
fun CuteTudeeTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val theme = if (isDarkTheme) darkThemeColors else lightThemeColors
    CompositionLocalProvider(
        LocalCuteTudeeLocalColors provides theme,
        ) {
        content()
    }
}