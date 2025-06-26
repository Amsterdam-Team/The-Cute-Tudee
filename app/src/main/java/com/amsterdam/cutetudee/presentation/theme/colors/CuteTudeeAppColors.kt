package com.amsterdam.cutetudee.presentation.theme.colors

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class CuteTudeeAppColors(
    val primary: Color,
    val primaryVariant: Color,
    val primaryGradientStart: Color,
    val primaryGradientEnd: Color,
    val secondary: Color,
    val title: Color,
    val body: Color,
    val hint: Color,
    val stroke: Color,
    val surfaceLow: Color,
    val surface: Color,
    val surfaceHigh: Color,
    val onPrimary: Color,
    val onPrimaryCaption: Color,
    val onPrimaryCard: Color,
    val onPrimaryStroke: Color,
    val disable: Color,
    val pinkAccent: Color,
    val yellowAccent: Color,
    val greenAccent: Color,
    val purpleAccent: Color,
    val error: Color,
    val overlay: Color,
    val emojiTint: Color,
    val yellowVariant: Color,
    val greenVariant: Color,
    val purpleVariant: Color,
    val errorVariant: Color,
    val dropShadowColor: Color,
    val switcherBackgroundColor: Color,
    val white40: Color,
)

internal val LocalCuteTudeeLocalColors = staticCompositionLocalOf { lightThemeColors }
