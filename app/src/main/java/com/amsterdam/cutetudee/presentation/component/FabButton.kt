package com.amsterdam.cutetudee.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme

@Composable
fun FabButton(modifier: Modifier = Modifier, painter: Painter,onClick: () -> Unit ) {
    val boxBackgroundColors = Brush.linearGradient(
        colors = listOf(
            AppTheme.color.primaryGradientStart,
            AppTheme.color.primaryGradientEnd
        ))
    Box(
        modifier = modifier
            .size(64.dp).clickable{ onClick()}
            .background(brush = boxBackgroundColors, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painter,
            contentDescription = null,
            tint = AppTheme.color.onPrimary,
            )
    }
}



@PreviewLightDark()
@Composable
private fun FabButtonPreview() {
    CuteTudeeTheme(isDarkTheme = isSystemInDarkTheme()) {
        FabButton(
            painter = painterResource(id = R.drawable.arrow_right_double_icon)
        ){}
    }
}