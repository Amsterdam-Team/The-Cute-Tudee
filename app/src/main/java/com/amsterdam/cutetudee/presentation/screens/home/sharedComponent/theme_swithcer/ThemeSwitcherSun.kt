package com.amsterdam.cutetudee.presentation.screens.home.sharedComponent.theme_swithcer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R

@Composable
fun BoxScope.MoonAfterAnimatingFromDark(
    isDarkTheme: Boolean,
    isAnimationRunning: Boolean,
    sunAnimatedAlignment: Alignment
) {
    AnimatedVisibility(
        visible = !isDarkTheme && !isAnimationRunning,
        enter = fadeIn(tween(0)),
        exit = fadeOut(tween(1000)),
        modifier = Modifier.align(sunAnimatedAlignment),
    ) {
        Sun()
    }
}

@Composable
fun BoxScope.SunWhileAnimatingFromDark(
    isDarkTheme: Boolean,
    isAnimationRunning: Boolean
) {
    AnimatedVisibility(
        visible = !isDarkTheme && isAnimationRunning,
        enter = fadeIn(tween(600, 400)),
        exit = fadeOut(tween(0)),
        modifier = Modifier.align(Alignment.CenterStart),
    ) {
        Sun()
    }
}

@Composable
private fun Sun() {
    Image(
        painter = painterResource(R.drawable.light_switcher_sun_image),
        contentDescription = null,
        modifier = Modifier.size(36.dp)
    )
}
