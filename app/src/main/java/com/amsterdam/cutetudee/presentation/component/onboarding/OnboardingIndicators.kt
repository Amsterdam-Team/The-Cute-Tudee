package com.amsterdam.cutetudee.presentation.component.onboarding

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews

@Composable
fun OnboardingIndicators(
    modifier: Modifier = Modifier,
    currentIndicator: Int,
    numberOfIndicators: Int = 3
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        (0 until numberOfIndicators).forEach { index ->
            val background by animateColorAsState(
                targetValue = if (currentIndicator == index) AppTheme.color.primary
                else AppTheme.color.primaryVariant, tween(1000)
            )
            Box(
                modifier = Modifier
                    .height(5.dp)
                    .weight(1f)
                    .background(
                        color = background, RoundedCornerShape(100.dp)
                    )
            )
        }
    }
}

@ThemeAndLocalePreviews ()
@Composable
private fun OnboardingCardPreviewDark() {
    CuteTudeeTheme() {
        OnboardingIndicators(currentIndicator = 2)
    }
}