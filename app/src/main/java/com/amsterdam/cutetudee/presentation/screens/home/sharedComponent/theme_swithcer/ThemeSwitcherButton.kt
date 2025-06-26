package com.amsterdam.cutetudee.presentation.screens.home.sharedComponent.theme_swithcer

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews
import com.amsterdam.cutetudee.presentation.utils.animateAlignmentAsState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ThemeSwitcherButton(isDark: Boolean, onSwitchTheme: () -> Unit) {
    var isDarkTheme by remember { mutableStateOf(isDark) }
    var isAnimationRunning by remember { mutableStateOf(false) }
    val backgroundColorAnimated by animateColorAsState(
        AppTheme.color.switcherBackgroundColor,
        tween(600, 400)
    )
    val sunAnimatedAlignment by animateAlignmentAsState(if (!isDarkTheme) Alignment.CenterStart else Alignment.CenterEnd)
    val moonAnimatedAlignment by animateAlignmentAsState(if (!isDarkTheme) Alignment.CenterStart else Alignment.CenterEnd)
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .size(64.dp, 36.dp)
            .clip(RoundedCornerShape(100.dp))
            .clipToBounds()
            .background(backgroundColorAnimated)
            .border(width = 1.dp, color = AppTheme.color.stroke, shape = RoundedCornerShape(100.dp))
            .combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                isAnimationRunning = true
                isDarkTheme = !isDarkTheme
                onSwitchTheme()
                coroutineScope.launch {
                    delay(1000)
                    isAnimationRunning = false
                }
            },
    ) {
        Stars(isDarkTheme)
        MoonWhileAnimatingFromLight(isDarkTheme, isAnimationRunning)
        SunWhileAnimatingFromDark(isDarkTheme, isAnimationRunning)
        Cloud4(isDarkTheme)
        Cloud5(isDarkTheme)
        Cloud2(isDarkTheme)
        Cloud1(isDarkTheme)
        Cloud3(isDarkTheme)
        MoonAfterAnimatingFromLight(isDarkTheme, isAnimationRunning, moonAnimatedAlignment)
        MoonCircle1(isDarkTheme)
        MoonCircle2(isDarkTheme)
        MoonCircle3(isDarkTheme)
        MoonAfterAnimatingFromDark(isDarkTheme, isAnimationRunning, sunAnimatedAlignment)
    }
}

@ThemeAndLocalePreviews
@Composable
private fun ThemeSwitcherButtonPreview() {
    CuteTudeeTheme {
        ThemeSwitcherButton(isSystemInDarkTheme()) {}
    }
}