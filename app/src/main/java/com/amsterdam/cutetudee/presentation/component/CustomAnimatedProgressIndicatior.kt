package com.amsterdam.cutetudee.presentation.component

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme

@Composable
fun CustomAnimatedProgressIndicatior(
    tint: Color,
    modifier: Modifier = Modifier,
) {
    val infiniteTransition = rememberInfiniteTransition()
    val animateFloat by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = InfiniteRepeatableSpec(tween(1000), RepeatMode.Restart),
    )
    Icon(
        imageVector = ImageVector.vectorResource(id = R.drawable.loading_icon),
        contentDescription = null,
        tint = tint,
        modifier =
            modifier
                .rotate(animateFloat),
    )
}

@Preview(name = "CustomAnimatedProgressIndecatior")
@Composable
private fun PreviewCustomAnimatedProgressIndecatior() {
    CustomAnimatedProgressIndicatior(
        tint = AppTheme.color.primary,
    )
}
