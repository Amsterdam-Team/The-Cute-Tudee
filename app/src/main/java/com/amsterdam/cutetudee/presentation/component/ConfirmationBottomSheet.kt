package com.amsterdam.cutetudee.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.utils.ThemeAndLocalePreviews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmationBottomSheet(
    isVisible: Boolean,
    onAction: () -> Unit,
    onCancel: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    onActionText: String = stringResource(R.string.delete),
    onCancelText: String = stringResource(R.string.cancel),
    title: String = stringResource(R.string.delete_task),
    description: String = stringResource(R.string.delete_description)
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { true }
    )

    AnimatedVisibility(isVisible) {
        CustomBottomSheet(
            modifier = modifier,
            onDismissRequest = onDismiss,
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ConfirmationMessageContainer(
                    title = title,
                    description = description,
                )
                ConfirmationButtonsContainer(
                    onAction = {
                        onAction()
                    },
                    onCancel = {
                        onCancel()
                    },
                    onActionText = onActionText,
                    onCancelText = onCancelText
                )
            }
        }
    }
}

@ThemeAndLocalePreviews
@Composable
fun ConfirmationBottomSheetPreview() {
    ConfirmationBottomSheet(isVisible = true, onAction = {}, onCancel = {}, onDismiss = {})
}

