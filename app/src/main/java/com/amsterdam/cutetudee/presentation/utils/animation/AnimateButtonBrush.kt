package com.amsterdam.cutetudee.presentation.utils.animation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.amsterdam.cutetudee.presentation.theme.AppTheme

@Composable
fun animateButtonBrush(
    isEnabled: Boolean,
    isNegative: Boolean,
    animationSpec: AnimationSpec<Color> = tween(300)
): Brush {
    val startColor by animateColorAsState(
        targetValue = when {
            !isEnabled -> AppTheme.color.disable
            isNegative -> AppTheme.color.errorVariant
            else -> AppTheme.color.primaryGradientStart
        },
        animationSpec = animationSpec,
        label = "startColor"
    )

    val endColor by animateColorAsState(
        targetValue = when {
            !isEnabled -> AppTheme.color.disable
            isNegative -> AppTheme.color.errorVariant
            else -> AppTheme.color.primaryGradientEnd
        },
        animationSpec = animationSpec,
        label = "endColor"
    )

    return Brush.verticalGradient(listOf(startColor, endColor))
}