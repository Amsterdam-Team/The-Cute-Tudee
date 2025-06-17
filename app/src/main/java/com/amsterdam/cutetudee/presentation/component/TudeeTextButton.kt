package com.amsterdam.cutetudee.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TudeeTextButton(
    title: String,
    onClick: () -> Unit,
    isLoading: Boolean,
    isNegative: Boolean,
    modifier: Modifier = Modifier,
    isDisabled: Boolean = false,
) {
    val isEnable = !isDisabled && !isLoading
    val contentColor =
        if (isDisabled) {
            AppTheme.color.disable
        } else if (isNegative) {
            AppTheme.color.error
        } else {
            AppTheme.color.primary
        }

    Row(
        modifier =
            modifier
                .clickable(enabled = isEnable, onClick = onClick)
                .animateContentSize(),
    ) {
        BasicText(
            text = title,
            style =
                AppTheme.textStyle.label.large
                    .merge(color = contentColor),
        )
        if (!isDisabled) {
            AnimatedVisibility(
                visible = isLoading,
                enter =
                    slideInHorizontally(
                        animationSpec = tween(durationMillis = 500),
                    ),
                exit =
                    slideOutHorizontally(tween(durationMillis = 0)),
                modifier = Modifier.padding(start = 4.dp),
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
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = 26.dp),
    ) {
        var isLoading by remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()
        TudeeTextButton(
            title = "Submit",
            onClick = {
                isLoading = true
                coroutineScope.launch {
                    delay(5000)
                    isLoading = false
                }
            },
            isLoading = isLoading,
            isNegative = true,
            modifier = Modifier,
        )
    }
}
