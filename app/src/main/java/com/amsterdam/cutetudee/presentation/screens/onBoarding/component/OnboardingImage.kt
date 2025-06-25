package com.amsterdam.cutetudee.presentation.screens.onBoarding.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R

@Composable
fun OnboardingImage(modifier: Modifier = Modifier, painter: Painter) {
    Image(
        modifier = modifier
            .fillMaxWidth()
            .height(260.dp),
        painter = painter,
        contentDescription = stringResource(R.string.content_description_onboarding_robot_base)
    )
}