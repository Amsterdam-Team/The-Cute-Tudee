package com.amsterdam.cutetudee.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TudeeOutlinedButton(
    text: String,
    onClick: () -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
) {
    val contentColor =
        if (isEnabled.not()) {
            AppTheme.color.stroke
        } else {
            AppTheme.color.primary
        }

    OutlinedButton(
        onClick = onClick,
        enabled = isEnabled,
        border =
            BorderStroke(
                width = 1.dp,
                color = AppTheme.color.stroke,
            ),
        colors =
            ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
            ),
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 24.dp),
        shape = RoundedCornerShape(100.dp),
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.animateContentSize(),
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
}

@Preview(name = "TudeeOutlinedButton", showBackground = true)
@Composable
private fun PreviewTudeeOutlinedButton() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().padding(horizontal = 26.dp),
    ) {
        var isLoading by remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()
        TudeeOutlinedButton(
            text = "Submit",
            onClick = {
                isLoading = true
                coroutineScope.launch {
                    delay(5000)
                    isLoading = false
                }
            },
            isLoading = isLoading,
            isEnabled = true,
            modifier = Modifier,
        )
    }
}
