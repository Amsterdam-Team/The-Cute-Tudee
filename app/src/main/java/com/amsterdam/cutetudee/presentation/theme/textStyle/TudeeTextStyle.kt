package com.amsterdam.cutetudee.presentation.theme.textStyle

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle

data class TudeeTextStyle(
    val headLine: SizedTextStyle,
    val title: SizedTextStyle,
    val body: SizedTextStyle,
    val label: SizedTextStyle,
    val appName: SizedTextStyle
)

data class SizedTextStyle(
    val large: TextStyle,
    val medium: TextStyle,
    val small: TextStyle
)

val LocalTudeeTextStyle = staticCompositionLocalOf { defaultTextStyle }