package com.amsterdam.cutetudee.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.presentation.theme.AppTheme

@Composable
fun GradientFilledButton(
    title: String,
    onClick: () -> Unit,
    isLoading: Boolean,
    isEnabled: Boolean,
    isError: Boolean,
    modifier: Modifier = Modifier,
) {
    val buttonBackgroundModifier =
        if (!isEnabled) {
            Modifier.background(AppTheme.color.disable)
        } else if (isError) {
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
        } else if (isError) {
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
        Text(
            text = title,
            color = contentColor,
            style = AppTheme.textStyle.label.large,
        )
        if (isLoading && isEnabled) {
            CustomAnimatedProgressIndicatior(
                tint = contentColor,
                modifier = Modifier.padding(start = 8.dp),
            )
        }
    }
}

@Preview(name = "FilledButton", showBackground = true)
@Composable
private fun PreviewFilledButton() {
    GradientFilledButton(
        title = "Submit",
        onClick = {},
        isLoading = false,
        isEnabled = true,
        isError = false,
    )
}
