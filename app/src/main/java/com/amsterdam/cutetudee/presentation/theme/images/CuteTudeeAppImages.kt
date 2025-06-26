package com.amsterdam.cutetudee.presentation.theme.images

import androidx.annotation.DrawableRes
import androidx.compose.runtime.staticCompositionLocalOf

data class CuteTudeeAppImages(
    @DrawableRes val onBoardingBackground: Int,
)

internal val LocalCuteTudeeLocalImages= staticCompositionLocalOf { lightThemeImages }
