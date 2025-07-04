package com.amsterdam.cutetudee.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews
import com.amsterdam.cutetudee.presentation.utils.animation.animateColorByMultipleConditions

@Composable
fun CustomTextButton(
    text: String,
    onClick: () -> Unit,
    isLoading: Boolean,
    isNegative: Boolean,
    modifier: Modifier = Modifier,
    isDisabled: Boolean = false,
) {
    val isEnable = !isDisabled && !isLoading

    val contentColor = animateColorByMultipleConditions(
        colorSelector = {
            when {
                isDisabled -> AppTheme.color.disable
                isNegative -> AppTheme.color.error
                else -> AppTheme.color.primary
            }
        }
    )

    Row(
        modifier =
            modifier
                .clip(RoundedCornerShape(100.dp))
                .clickable(
                    remember { MutableInteractionSource() },
                    null,
                    enabled = isEnable,
                    onClick = onClick
                )
                .animateContentSize(),
    ) {
        Text(
            text = text,
            color = contentColor,
            style = AppTheme.textStyle.label.large,
        )

        val isVisible = isLoading && !isDisabled
        AnimatedVisibility(
            visible = isVisible,
            enter =
                slideInHorizontally(
                    animationSpec = tween(durationMillis = 500),
                ),
            exit =
                slideOutHorizontally(tween(durationMillis = 0)),
            modifier = Modifier.padding(start = 4.dp),
        ) {
            CustomAnimatedProgressIndicator(
                tint = contentColor,
            )
        }
    }
}

@ThemeAndLocalePreviews
@Composable
private fun TudeeOutlinedButtonPreview() {
    CuteTudeeTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 26.dp),
        ) {
            CustomTextButton(
                text = "Submit",
                onClick = {},
                isLoading = false,
                isNegative = false,
                isDisabled = false,
                modifier = Modifier,
            )
        }
    }
}
