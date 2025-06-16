package com.amsterdam.cutetudee.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.presentation.theme.AppTheme

@Composable
fun TudeeTextButton(
    title: String,
    onClick: () -> Unit,
    isLoading: Boolean,
    isEnabled: Boolean,
    isError: Boolean,
    modifier: Modifier = Modifier,
) {
    val contentColor =
        if (!isEnabled) {
            AppTheme.color.disable
        } else if (isError) {
            AppTheme.color.error
        } else {
            AppTheme.color.primary
        }

    TextButton(
        onClick = onClick,
        enabled = isEnabled,
        modifier = modifier,
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

@Preview(name = "TudeeOutlinedButton", showBackground = true)
@Composable
private fun PreviewTudeeOutlinedButton() {
    TudeeTextButton(
        title = "Submit",
        onClick = {},
        isLoading = true,
        isEnabled = true,
        isError = false,
    )
}
