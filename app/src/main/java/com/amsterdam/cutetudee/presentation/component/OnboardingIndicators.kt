package com.amsterdam.cutetudee.presentation.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme


@Composable
fun OnboardingIndicators(
    modifier: Modifier = Modifier,
    currentIndicator: Int
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        (0..2).forEach { index ->
            val background by animateColorAsState(
                targetValue = if (currentIndicator == index) AppTheme.color.primary
                else AppTheme.color.primaryVariant, tween(1000)
            )
            Indicator(
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

@Composable
private fun Indicator(modifier: Modifier = Modifier) {
    Box(modifier.height(5.dp)) {}
}

@PreviewLightDark()
@Composable
private fun OnboardingCardPreviewDark() {
    CuteTudeeTheme(isDarkTheme = isSystemInDarkTheme()) {
         OnboardingIndicators(currentIndicator = 2)
    }
}