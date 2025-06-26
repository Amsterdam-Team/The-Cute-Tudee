package com.amsterdam.cutetudee.presentation.utils.animation

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment

@SuppressLint("UnrememberedMutableState")
@Composable
fun animateAlignmentAsState(
    targetAlignment: Alignment,
): State<Alignment> {
    val biased = targetAlignment as BiasAlignment
    val horizontal by animateFloatAsState(
        biased.horizontalBias,
        animationSpec = tween(durationMillis = 1000)
    )
    val vertical by animateFloatAsState(
        biased.verticalBias,
        animationSpec = tween(durationMillis = 1000)
    )
    return derivedStateOf { BiasAlignment(horizontal, vertical) }
}