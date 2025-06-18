package com.amsterdam.cutetudee.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.amsterdam.cutetudee.R
import com.amsterdam.cutetudee.presentation.theme.AppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteConfirmationBottomSheet(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    onDelete: () -> Unit,
    onCancel: () -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { true }
    )
    val coroutineScope = rememberCoroutineScope()
    val dismissBottomSheet = remember {
        {
            coroutineScope.launch {
                sheetState.hide()
            }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    onDismiss()
                }
            }
        }
    }

    if (isVisible) {
        CustomBottomSheet(
            modifier = modifier,
            onDismissRequest = { dismissBottomSheet() },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ConfirmationMessageContainer()
                ConfirmationButtonContainer(
                    onDelete = {
                        onDelete()
                        dismissBottomSheet()
                    },
                    onCancel = {
                        onCancel()
                        dismissBottomSheet()
                    }
                )
            }
        }
    }
}

@Composable
private fun ConfirmationButtonContainer(
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .shadow(
                elevation = 4.dp,
                shape = RectangleShape,
                ambientColor = AppTheme.color.dropShadowColor,
                spotColor = AppTheme.color.dropShadowColor,
                clip = false
            )
            .background(AppTheme.color.surfaceHigh)
            .border(
                width = 1.dp,
                color = AppTheme.color.surfaceHigh
            )
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp
            )

    ) {
        GradientFilledButton(
            title = stringResource(R.string.delete),
            onClick = onDelete,
            isLoading = false,
            isNegative = true,
            modifier = Modifier.fillMaxWidth(),
        )
        OutlineButton(
            text = stringResource(R.string.cancel),
            onClick = onCancel,
            isLoading = false,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun ConfirmationMessageContainer(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(AppTheme.color.surface)
            .padding(horizontal = 16.dp)
    )
    {
        Text(
            modifier = Modifier.padding(bottom = 12.dp),
            text = stringResource(R.string.delete_task),
            style = AppTheme.textStyle.title.large,
            color = AppTheme.color.title
        )
        Text(
            modifier = Modifier.padding(bottom = 12.dp),
            text = stringResource(R.string.delete_description),
            style = AppTheme.textStyle.body.large,
            color = AppTheme.color.body
        )
        Image(
            painter = painterResource(R.drawable.tudee_image_shocking),
            contentDescription = null,
            modifier = Modifier
                .padding(bottom = 24.dp)
                .width(107.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

