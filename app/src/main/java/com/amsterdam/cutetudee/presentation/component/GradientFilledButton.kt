package com.amsterdam.cutetudee.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.theme.CuteTudeeTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews
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
    paddingValues: PaddingValues = PaddingValues(vertical = 8.dp, horizontal = 24.dp)
) {

    val baseModifier = Modifier
        .clip(CircleShape)
        .fillMaxWidth()
        .height(56.dp)
    val buttonBackgroundModifier =
        if (!isEnabled) baseModifier.background(AppTheme.color.disable)
        else if (isNegative) baseModifier.background(AppTheme.color.errorVariant)
        else {
            baseModifier.background(
                Brush.verticalGradient(
                    listOf(
                        AppTheme.color.primaryGradientStart,
                        AppTheme.color.primaryGradientEnd,
                    ),
                ),
            )
        }

    val contentColor by animateColorAsState(
        targetValue = if (!isEnabled) AppTheme.color.stroke
        else if (isNegative) AppTheme.color.error
        else AppTheme.color.onPrimary
    )

    Row(
        modifier = modifier
            .then(buttonBackgroundModifier)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(),
                onClick = onClick,
                enabled = isEnabled && !isLoading,
            )
            .animateContentSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            color = contentColor,
            style = AppTheme.textStyle.label.large,
        )

        AnimatedVisibility(
            visible = isLoading && isEnabled,
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
private fun FilledButtonPreview() {
    CuteTudeeTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 26.dp),
        ) {
            var isLoading by remember { mutableStateOf(false) }
            val coroutineScope = rememberCoroutineScope()
            GradientFilledButton(
                title = stringResource(R.string.add),
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
}
