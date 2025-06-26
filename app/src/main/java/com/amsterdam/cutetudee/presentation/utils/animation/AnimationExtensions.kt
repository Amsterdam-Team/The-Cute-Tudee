package com.amsterdam.cutetudee.presentation.utils.animation

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

enum class SlideDirection {
    Up, Down, End, Start, Left, Right
}

fun Modifier.scale(
    from: Float = 0.5f,
    to: Float = 1f,
    durationMillis: Int = 400,
    delayMillis: Int = 0
): Modifier = composed {
    var isAnimated by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isAnimated) to else from,
        animationSpec = tween(durationMillis = durationMillis),
        label = "scaleAnimation"
    )

    LaunchedEffect(key1 = true) {
        delay(delayMillis.toLong())
        isAnimated = true
    }

    graphicsLayer {
        scaleX = scale
        scaleY = scale
    }
}

fun Modifier.slide(
    direction: SlideDirection,
    distance: Dp = 10.dp,
    durationMillis: Int = 550,
    delayMillis: Int = 0,
    animationSpec: AnimationSpec<Float> = tween(durationMillis = durationMillis)
): Modifier = composed {
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    var isAnimated by remember { mutableStateOf(false) }
    val offsetX by animateFloatAsState(
        targetValue = if (isAnimated) {
            0f
        } else {
            when (direction) {
                SlideDirection.Start -> if (isRtl) distance.value else -distance.value
                SlideDirection.End -> if (isRtl) -distance.value else distance.value
                SlideDirection.Left -> distance.value
                SlideDirection.Right -> -distance.value
                else -> 0f
            }
        },
        animationSpec = animationSpec,
        label = "swipeAnimationX"
    )
    val offsetY by animateFloatAsState(
        targetValue = if (isAnimated) {
            0f
        } else {
            when (direction) {
                SlideDirection.Up -> distance.value
                SlideDirection.Down -> -distance.value
                else -> 0f
            }
        },
        animationSpec = animationSpec,
        label = "swipeAnimationY"
    )

    LaunchedEffect(key1 = Unit) {
        delay(delayMillis.toLong())
        isAnimated = true
    }

    this.offset(x = offsetX.dp, y = offsetY.dp)
}

fun Modifier.fadeAnimation(
    from: Float = 0f,
    to: Float = 1f,
    durationMillis: Int = 400
): Modifier = composed {
    var isAnimated by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (isAnimated) to else from,
        animationSpec = tween(durationMillis = durationMillis),
        label = "fadeAnimation"
    )

    LaunchedEffect(Unit) {
        isAnimated = true
    }

    graphicsLayer {
        this.alpha = alpha
    }
}