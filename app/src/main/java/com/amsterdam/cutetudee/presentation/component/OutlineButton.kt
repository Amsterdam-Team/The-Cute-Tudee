package com.amsterdam.cutetudee.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme

@Composable
fun OutlineButton(
    text: String,
    onClick: () -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
) {
    val contentColor =
        if (!isEnabled) {
            AppTheme.color.stroke
        } else {
            AppTheme.color.primary
        }
    Row(
        modifier = modifier
            .padding(top = 12.dp)
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = AppTheme.color.stroke,
                shape = RoundedCornerShape(100.dp)
            )
            .padding(vertical = 12.dp)
            .animateContentSize()
            .clickable(onClick = onClick, enabled = isEnabled),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            color = contentColor,
            style = AppTheme.textStyle.label.large,
        )

        val isVisible = isEnabled && isLoading
        AnimatedVisibility(
            visible = isVisible,
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

@Preview(showBackground = true)
@Composable
private fun OutlineButtonPreview() {
    OutlineButton(
        text = stringResource(R.string.cancel),
        onClick = {},
        isLoading = false
    )
}