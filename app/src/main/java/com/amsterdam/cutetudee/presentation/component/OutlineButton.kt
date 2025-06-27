package com.amsterdam.cutetudee.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews
import com.amsterdam.cutetudee.presentation.utils.animation.animateColor

@Composable
fun OutlineButton(
    text: String,
    onClick: () -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    textButtonPadding: PaddingValues = PaddingValues(vertical = 12.dp)
) {
    val contentColor = animateColor(condition = !isEnabled, trueColor = AppTheme.color.stroke, falseColor = AppTheme.color.primary)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(
                width = 1.dp,
                color = AppTheme.color.stroke,
                shape = RoundedCornerShape(100.dp)
            )
            .clip(shape = RoundedCornerShape(100.dp))
            .clipToBounds()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(),
                onClick = onClick,
                enabled = isEnabled && !isLoading
            )
            .padding(paddingValues = textButtonPadding)
            .animateContentSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            color = contentColor,
            style = AppTheme.textStyle.label.large,
        )
        AnimatedVisibility(
            visible = isEnabled && isLoading,
            enter =
                slideInHorizontally(
                    animationSpec = tween(durationMillis = 500),
                ),
            exit =
                slideOutHorizontally(tween(durationMillis = 0)),
            modifier = Modifier.padding(start = 8.dp),
        ) {
            CustomAnimatedProgressIndicator(
                tint = contentColor,
            )
        }
    }
}

@ThemeAndLocalePreviews
@Composable
private fun OutlineButtonPreview() {
    CuteTudeeTheme {
        OutlineButton(
            text = stringResource(R.string.cancel),
            onClick = {},
            isLoading = false
        )
    }
}