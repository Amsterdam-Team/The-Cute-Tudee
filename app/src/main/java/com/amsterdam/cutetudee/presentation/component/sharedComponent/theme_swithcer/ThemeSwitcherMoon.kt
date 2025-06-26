package com.amsterdam.cutetudee.presentation.component.sharedComponent.theme_swithcer

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
fun BoxScope.MoonAfterAnimatingFromLight(
    isDarkTheme: Boolean,
    isAnimationRunning: Boolean,
    moonAnimatedAlignment: Alignment
) {
    AnimatedVisibility(
        visible = isDarkTheme && !isAnimationRunning,
        enter = fadeIn(tween(0)),
        exit = fadeOut(tween(1000)),
        modifier = Modifier.align(moonAnimatedAlignment),
    ) {
        Moon()
    }
}

@Composable
fun BoxScope.MoonWhileAnimatingFromLight(
    isDarkTheme: Boolean,
    isAnimationRunning: Boolean
) {
    AnimatedVisibility(
        visible = isDarkTheme && isAnimationRunning,
        enter = fadeIn(tween(600, 400)),
        exit = fadeOut(tween(0)),
        modifier = Modifier.align(Alignment.CenterEnd),
    ) {
        Moon()
    }
}

@Composable
private fun Moon() {
    Image(
        painter = painterResource(R.drawable.dark_switcher_moon_image_1),
        contentDescription = "moon",
        modifier = Modifier.size(36.dp)
    )
}