package com.amsterdam.cutetudee.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GradientFilledButton(
    title: String,
    onClick: () -> Unit,
    isLoading: Boolean,
    isNegative: Boolean,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
) {
    val buttonBackgroundModifier =
        if (!isEnabled) {
            Modifier.background(AppTheme.color.disable)
        } else if (isNegative) {
            Modifier.background(AppTheme.color.errorVariant)
        } else {
            Modifier.background(
                Brush.verticalGradient(
                    listOf(
                        AppTheme.color.primaryGradientStart,
                        AppTheme.color.primaryGradientEnd,
                    ),
                ),
            )
        }

    val contentColor =
        if (!isEnabled) {
            AppTheme.color.stroke
        } else if (isNegative) {
            AppTheme.color.error
        } else {
            AppTheme.color.onPrimary
        }

    Button(
        onClick = onClick,
        enabled = isEnabled,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
            ),
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 24.dp),
        modifier =
            modifier
                .clip(RoundedCornerShape(100.dp))
                .then(buttonBackgroundModifier),
    ) {
        Row(
            modifier = Modifier.animateContentSize(),
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
                            animationSpec = tween(durationMillis = 500),
                        ),
                    exit =
                        slideOutHorizontally(tween(durationMillis = 0)),
                    modifier = Modifier.padding(start = 8.dp),
                ) {
                    CustomAnimatedProgressIndicatior(
                        tint = contentColor,
                    )
                }
            }
        }
    }
}

@Preview(name = "FilledButton", showBackground = true)
@Composable
private fun PreviewFilledButton() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().padding(horizontal = 26.dp),
    ) {
        var isLoading by remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()
        GradientFilledButton(
            title = "Submit",
            onClick = {
                isLoading = true
                coroutineScope.launch {
                    delay(5000)
                    isLoading = false
                }
            },
            isLoading = isLoading,
            isNegative = false,
            modifier = Modifier,
        )
    }
}
