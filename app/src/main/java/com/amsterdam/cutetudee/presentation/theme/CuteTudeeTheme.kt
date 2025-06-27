@file:Suppress("DEPRECATION")

package com.amsterdam.cutetudee.presentation.theme

import android.os.Build
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.amsterdam.cutetudee.presentation.theme.colors.LocalCuteTudeeLocalColors
import com.amsterdam.cutetudee.presentation.theme.colors.darkThemeColors
import com.amsterdam.cutetudee.presentation.theme.colors.lightThemeColors
import com.amsterdam.cutetudee.presentation.theme.images.LocalCuteTudeeLocalImages
import com.amsterdam.cutetudee.presentation.theme.images.darkThemeImages
import com.amsterdam.cutetudee.presentation.theme.images.lightThemeImages

@Composable
fun CuteTudeeTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val theme = if (isDarkTheme) darkThemeColors else lightThemeColors
    val images = if (isDarkTheme) darkThemeImages else lightThemeImages

    val activity = LocalActivity.current
    val view = LocalView.current

    if (activity != null) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O){
            activity.window.navigationBarColor = theme.surfaceHigh.toArgb()
        }
        WindowCompat.getInsetsController(activity.window, view).isAppearanceLightStatusBars = !isDarkTheme
    }

    CompositionLocalProvider(
        LocalCuteTudeeLocalColors provides theme,
        LocalCuteTudeeLocalImages provides images,
        LocalIsDarkTheme provides isDarkTheme
    ) {
        content()
    }
}

internal val LocalIsDarkTheme = compositionLocalOf<Boolean> {
    error("LocalIsDarkTheme not provided")
}