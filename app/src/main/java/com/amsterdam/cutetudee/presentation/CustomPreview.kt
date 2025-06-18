package com.amsterdam.cutetudee.presentation

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme

@Preview(
    name = "Light Theme - English",
    group = "Themes and Locales",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "en",
)
@Preview(
    name = "Dark Theme - English",
    group = "Themes and Locales",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    locale = "en",
)
@Preview(
    name = "Light Theme - Arabic",
    group = "Themes and Locales",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "ar",
)
@Preview(
    name = "Dark Theme - Arabic",
    group = "Themes and Locales",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    locale = "ar",
)
annotation class ThemeAndLocalePreviews

@Composable
fun CustomPreview(content: @Composable () -> Unit) {
    CuteTudeeTheme(isDarkTheme = isSystemInDarkTheme()) {
        content()
    }
}