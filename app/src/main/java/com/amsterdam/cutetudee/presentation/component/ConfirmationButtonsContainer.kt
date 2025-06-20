package com.amsterdam.cutetudee.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews
import com.amsterdam.cutetudee.presentation.utils.dropShadow

@Composable
fun ConfirmationButtonsContainer(
    onAction: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
    onActionText: String = stringResource(R.string.delete),
    onCancelText: String = stringResource(R.string.cancel)
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .dropShadow(
                blur = 20.dp,
                shape = RectangleShape,
                color = AppTheme.color.dropShadowColor
            )
            .background(AppTheme.color.surfaceHigh)
            .border(
                width = 1.dp,
                color = AppTheme.color.surfaceHigh
            )
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp)

    ) {
        GradientFilledButton(
            title = onActionText,
            onClick = onAction,
            isLoading = false,
            isNegative = true,
            modifier = Modifier.fillMaxWidth(),
        )
        OutlineButton(
            text = onCancelText,
            onClick = onCancel,
            isLoading = false,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@ThemeAndLocalePreviews
@Composable
fun ConfirmationButtonsContainerPreview() {
    ConfirmationButtonsContainer(onAction = {}, onCancel = {})
}