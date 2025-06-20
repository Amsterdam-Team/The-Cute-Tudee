package com.amsterdam.cutetudee.presentation.screens.home.component.theme_swithcer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.offset
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
fun BoxScope.Cloud1(isDarkTheme: Boolean) {
    AnimatedVisibility(
        visible = !isDarkTheme,
        enter = slideIn(initialOffset = {
            IntOffset(
                (-25).dp.value.toInt(),
                (10).dp.value.toInt()
            )
        }, animationSpec = tween(600, 400)) + fadeIn(tween(600, 400)) + scaleIn(
            initialScale = 0.5f,
            animationSpec = tween(600, 400)
        ),
        exit = slideOut(targetOffset = {
            IntOffset(
                (-25).dp.value.toInt(),
                (10).dp.value.toInt()
            )
        }, animationSpec = tween(1000)) + fadeOut(tween(1000)) + scaleOut(
            targetScale = 0.5f,
            animationSpec = tween(1000)
        ),
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(end = 14.5.dp),
    ) {
        Image(
            painter = painterResource(R.drawable.light_switcher_image_1),
            contentDescription = null,
            modifier = Modifier
                .size(14.dp, 16.dp)
                .offset(
                    y = (animateFloatAsState(
                        if (isDarkTheme) 2f else 4f,
                        tween(600, 400)
                    ).value.dp)
                )
        )
    }
}

@Composable
fun BoxScope.Cloud2(isDarkTheme: Boolean) {
    AnimatedVisibility(
        visible = !isDarkTheme,
        enter = slideIn(initialOffset = {
            IntOffset(
                (48).dp.value.toInt(),
                (38).dp.value.toInt()
            )
        }, animationSpec = tween(600, 400)) + fadeIn(tween(600, 400)),
        exit = slideOut(targetOffset = {
            IntOffset(
                (48).dp.value.toInt(),
                (38).dp.value.toInt()
            )
        }, animationSpec = tween(1000)) + fadeOut(tween(1000)),
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(end = 1.dp),
    ) {
        Image(
            painter = painterResource(R.drawable.light_switcher_image_2),
            contentDescription = null,
            modifier = Modifier
                .size(16.dp, 16.dp)
                .offset(y = (5).dp)
        )
    }
}

@Composable
fun BoxScope.Cloud3(isDarkTheme: Boolean) {
    AnimatedVisibility(
        visible = !isDarkTheme,
        enter = fadeIn(tween(1, 600)) + scaleIn(
            initialScale = 0.276f,
            animationSpec = tween(1, 600)
        ),
        exit = fadeOut(tween(0, 1000)) + scaleOut(
            targetScale = 0.276f,
            animationSpec = tween(0, 1000)
        ),
        modifier = Modifier.align(Alignment.TopEnd)
    ) {
        Image(
            painter = painterResource(R.drawable.light_switcher_image_3),
            contentDescription = null,
            modifier = Modifier
                .size(29.dp, 29.dp)
                .offset(
                    x = (animateFloatAsState(
                        if (!isDarkTheme) 13.5f else 0f,
                        tween(600, 400)
                    ).value.dp)
                )
        )
    }
}

@Composable
fun BoxScope.Cloud4(isDarkTheme: Boolean) {
    AnimatedVisibility(
        visible = !isDarkTheme,
        enter = slideIn(initialOffset = {
            IntOffset(
                (-2).dp.value.toInt(),
                (90).dp.value.toInt()
            )
        }, animationSpec = tween(600, 400)) + fadeIn(tween(600, 400)),
        exit = slideOut(targetOffset = {
            IntOffset(
                (-2).dp.value.toInt(),
                (90).dp.value.toInt()
            )
        }, animationSpec = tween(1000)) + fadeOut(tween(1000)),
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(end = 8.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.light_switcher_image_4),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp, 24.dp)
                .offset(y = 8.dp)
        )
    }
}

@Composable
fun BoxScope.Cloud5(isDarkTheme: Boolean) {
    AnimatedVisibility(
        visible = !isDarkTheme,
        enter = slideIn(initialOffset = {
            IntOffset(
                (48).dp.value.toInt(),
                (38).dp.value.toInt()
            )
        }, animationSpec = tween(600, 400)) + fadeIn(tween(600, 400)),
        exit = slideOut(targetOffset = {
            IntOffset(
                (48).dp.value.toInt(),
                (38).dp.value.toInt()
            )
        }, animationSpec = tween(1000)) + fadeOut(tween(1000)),
        modifier = Modifier.align(Alignment.TopEnd)
    ) {
        Image(
            painter = painterResource(R.drawable.light_switcher_image_5),
            contentDescription = null,
            modifier = Modifier
                .size(32.dp, 32.dp)
                .offset(x = (13).dp, y = (-3).dp)
        )
    }
}