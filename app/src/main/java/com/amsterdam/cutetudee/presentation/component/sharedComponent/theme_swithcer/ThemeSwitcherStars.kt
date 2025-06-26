package com.amsterdam.cutetudee.presentation.component.sharedComponent.theme_swithcer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R

@Composable
fun BoxScope.Stars(isDarkTheme: Boolean) {
    AnimatedVisibility(
        visible = isDarkTheme,
        enter = fadeIn(tween(200, 300)) + scaleIn(tween(600), 4f),
        exit = fadeOut(tween(1000)) + scaleOut(tween(1000), 3f),
        modifier = Modifier.padding(start = 6.dp, top = 6.dp, bottom = 4.dp),
    ) {
        Image(
            painter = painterResource(R.drawable.dark_switcher_stars),
            contentDescription = null,
            modifier = Modifier.size(28.dp)
        )
    }
}
