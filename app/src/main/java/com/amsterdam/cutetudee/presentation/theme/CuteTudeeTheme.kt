package com.amsterdam.cutetudee.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.amsterdam.cutetudee.presentation.theme.colors.CuteTudeeLocalColors
import com.amsterdam.cutetudee.presentation.theme.colors.darkThemeColors
import com.amsterdam.cutetudee.presentation.theme.colors.lightThemeColors

@Composable
fun CuteTudeeTheme(
    isDarkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val theme = if (isDarkTheme) darkThemeColors else lightThemeColors
    CompositionLocalProvider(
        CuteTudeeLocalColors provides theme,

        ) {
        content()
    }
}