package com.amsterdam.cutetudee.presentation.screens.home.component.theme_swithcer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R

@Composable
fun BoxScope.MoonCircle1(isDarkTheme: Boolean) {
    AnimatedVisibility(
        visible = isDarkTheme,
        enter = fadeIn(tween(600, 400)),
        exit = fadeOut(tween(1000)),
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(bottom = 8.dp, end = 16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.dark_switcher_moon_image_3),
            contentDescription = null,
            modifier = Modifier.size(14.dp, 14.dp)
        )
    }
}

@Composable
fun BoxScope.MoonCircle2(isDarkTheme: Boolean) {
    AnimatedVisibility(
        visible = isDarkTheme,
        enter = slideIn(initialOffset = {
            IntOffset(
                (58).dp.value.toInt(),
                (25).dp.value.toInt()
            )
        }, animationSpec = tween(1000)) + fadeIn(tween(400)) + scaleIn(
            initialScale = 3.625f,
            animationSpec = tween(1000)
        ),
        exit = slideOut(targetOffset = {
            IntOffset(
                (58).dp.value.toInt(),
                (25).dp.value.toInt()
            )
        }, animationSpec = tween(1000)) + fadeOut(tween(400, 600)) + scaleOut(
            targetScale = 3.625f,
            animationSpec = tween(1000)
        ),
        modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(top = 4.dp, end = 17.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.dark_switcher_moon_image_2),
            contentDescription = null,
            modifier = Modifier.size(8.dp)
        )
    }
}

@Composable
fun BoxScope.MoonCircle3(isDarkTheme: Boolean) {
    AnimatedVisibility(
        visible = isDarkTheme,
        enter = fadeIn(tween(600, 400)),
        exit = fadeOut(tween(1000)),
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(bottom = 6.dp, end = 11.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.dark_switcher_moon_image_4),
            contentDescription = null,
            modifier = Modifier.size(4.dp)
        )
    }
}