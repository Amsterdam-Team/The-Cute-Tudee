package com.amsterdam.cutetudee.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.presentation.theme.AppTheme

@Composable
fun TudeeTextButton(
    title: String,
    onClick: () -> Unit,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
) {
    var isLoading by remember { mutableStateOf(false) }

    val contentColor =
        if (!isEnabled) {
            AppTheme.color.disable
        } else {
            AppTheme.color.primary
        }

    TextButton(
        onClick = {
            if (isLoading) return@TextButton
            isLoading = true
            onClick()
        },
        enabled = isEnabled,
        modifier =
            modifier.animateContentSize(
                animationSpec =
                    spring(
                        dampingRatio = 0.8f,
                        stiffness = 30f,
                    ),
            ),
    ) {
        Text(
            text = title,
            color = contentColor,
            style = AppTheme.textStyle.label.large,
        )
        if (isEnabled) {
            AnimatedVisibility(
                visible = isLoading,
                enter =
                    slideInHorizontally(
                        animationSpec = tween(durationMillis = 800),
                    ),
                exit =
                    slideOutHorizontally(tween()),
                modifier = Modifier.padding(start = 8.dp),
            ) {
                CustomAnimatedProgressIndicatior(
                    tint = contentColor,
                )
            }
        }
    }
}

@Preview(name = "TudeeOutlinedButton", showBackground = true)
@Composable
private fun PreviewTudeeOutlinedButton() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        TudeeTextButton(
            title = "Submit",
            onClick = {},
            isEnabled = true,
        )
    }
}
